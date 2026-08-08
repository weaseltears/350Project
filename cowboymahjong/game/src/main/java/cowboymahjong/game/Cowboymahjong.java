package cowboymahjong.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.scene.Spatial;

import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.collision.CollisionResults;

import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;

import java.util.ArrayList;
import java.util.Collections;

public class Cowboymahjong extends SimpleApplication implements TileHoverListener {

    private ArrayList<MahjongTile> deck = new ArrayList<>();

    // handSpatials[i] and handSlotX[i] describe hand slot i:
    // which spatial currently occupies it, and its fixed x position.
    private ArrayList<Spatial> handSpatials = new ArrayList<>();
    private ArrayList<Float> handSlotX = new ArrayList<>();

    private ArrayList<RisingTile> risingTiles = new ArrayList<>();

    private static final float LIFT_HEIGHT = 0.5f;
    private static final float RISE_START_Y = -2f;
    private static final float RISE_DURATION = 0.4f;

    private final TileHoverSubject hoverSubject = new TileHoverSubject();

    public Cowboymahjong() { }

    public Cowboymahjong(AppState... initialStates) {
        super(initialStates);
    }

    private void createDeck() {
        for (int value = 1; value <= 9; value++) {
            for (int copy = 0; copy < 4; copy++) {
                deck.add(new MahjongTile("Textures/dot_" + value + ".png"));
            }
        }
        for (int value = 1; value <= 9; value++) {
            for (int copy = 0; copy < 4; copy++) {
                deck.add(new MahjongTile("Textures/bamboo_" + value + ".png"));
            }
        }
        for (int value = 1; value <= 9; value++) {
            for (int copy = 0; copy < 4; copy++) {
                deck.add(new MahjongTile("Textures/char_" + value + ".png"));
            }
        }
        for (int i = 0; i < 8; i++) {
            deck.add(new MahjongTile("Textures/joker.png"));
        }
        Collections.shuffle(deck);
    }

    private ArrayList<MahjongTile> drawHand(int size) {
        ArrayList<MahjongTile> hand = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            hand.add(deck.remove(0));
        }
        return hand;
    }

    @Override
    public void simpleInitApp() {
        createDeck();

        float spacing = 1.5f;
        float startX = -2 * spacing;

        ArrayList<MahjongTile> hand = drawHand(5);

        for (int i = 0; i < hand.size(); i++) {
            float x = startX + i * spacing;

            Spatial tile = new TileBuilder(assetManager)
                    .modelPath("Models/tile.glb")
                    .texture(hand.get(i).getTexturePath())
                    .position(x, 0, 0)
                    .scale(1f)
                    .rotateX(30)
                    .userData("isTile", true)
                    .build();

            rootNode.attachChild(tile);
            handSpatials.add(tile);
            handSlotX.add(x);
        }

        cam.setLocation(new Vector3f(0, 2, 8));
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);

        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);

        hoverSubject.addListener(this);

        setUpDiscardInput();
    }

    private void setUpDiscardInput() {
        inputManager.addMapping("DiscardTile", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(discardListener, "DiscardTile");
    }

    private final ActionListener discardListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("DiscardTile") && isPressed) {
                discardHoveredTile();
            }
        }
    };

    private void discardHoveredTile() {
        Spatial clicked = getHoveredTile();
        if (clicked == null) {
            return;
        }

        int slot = handSpatials.indexOf(clicked);
        if (slot == -1) {
            return;
        }

        // Remove the discarded tile from the scene
        clicked.removeFromParent();

        // Clear hover state so simpleUpdate doesn't keep trying to lift
        // a spatial that no longer exists.
        hoverSubject.updateHover(null);

        if (deck.isEmpty()) {
            return; // no more tiles to draw; slot stays empty
        }

        MahjongTile newTileData = deck.remove(0);
        float x = handSlotX.get(slot);

        Spatial newTile = new TileBuilder(assetManager)
                .modelPath("Models/tile.glb")
                .texture(newTileData.getTexturePath())
                .position(x, RISE_START_Y, 0)
                .scale(1f)
                .rotateX(30)
                .userData("isTile", true)
                .build();

        rootNode.attachChild(newTile);
        handSpatials.set(slot, newTile);

        risingTiles.add(new RisingTile(newTile, RISE_START_Y, 0f, RISE_DURATION));
    }

    private Spatial getHoveredTile() {
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(click2d, 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(click2d, 1f).subtractLocal(click3d).normalizeLocal();

        Ray ray = new Ray(click3d, dir);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);

        if (results.size() > 0) {
            return findTileRoot(results.getClosestCollision().getGeometry());
        }
        return null;
    }

    @Override
    public void simpleUpdate(float tpf) {
        Spatial newHovered = getHoveredTile();
        hoverSubject.updateHover(newHovered);

        updateRisingTiles(tpf);
    }

    private void updateRisingTiles(float tpf) {
        for (int i = risingTiles.size() - 1; i >= 0; i--) {
            RisingTile rt = risingTiles.get(i);
            boolean finished = rt.update(tpf);
            if (finished) {
                risingTiles.remove(i);
            }
        }
    }

    @Override
    public void onTileHoverChanged(Spatial previousTile, Spatial newTile) {
        if (previousTile != null) {
            Vector3f pos = previousTile.getLocalTranslation();
            previousTile.setLocalTranslation(pos.x, 0, pos.z);
        }
        if (newTile != null) {
            Vector3f pos = newTile.getLocalTranslation();
            newTile.setLocalTranslation(pos.x, LIFT_HEIGHT, pos.z);
        }
    }

    private Spatial findTileRoot(Spatial s) {
        while (s != null) {
            if (Boolean.TRUE.equals(s.getUserData("isTile"))) {
                return s;
            }
            s = s.getParent();
        }
        return null;
    }
}