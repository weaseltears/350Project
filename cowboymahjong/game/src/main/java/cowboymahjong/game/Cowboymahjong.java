package cowboymahjong.game;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.app.state.AppState;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class Cowboymahjong extends SimpleApplication {
	//deck builder variable
	private ArrayList<MahjongTile> deck = new ArrayList<>();
	
    public Cowboymahjong() {
    	
    }
    
    public Cowboymahjong(AppState... initialStates) {
        super(initialStates);
    }
    
    private void createDeck() {
    	//Dots
    	for (int value = 1; value <= 9; value++) {
    		for (int copy = 0; copy <4; copy++) {
    			deck.add(new MahjongTile("Textures/dot_" + value + ".png"));
    		}
    	}
    	
    	//Bamboo
    	for (int value = 1; value <= 9; value++) {
    		for (int copy = 0; copy <4; copy++) {
    			deck.add(new MahjongTile("Textures/bamboo_" + value + ".png"));
    		}
    	}
    	
    	//Characters
    	for (int value = 1; value <= 9; value++) {
    		for (int copy = 0; copy <4; copy++) {
    			deck.add(new MahjongTile("Textures/char_" + value + ".png"));
    		}
    	}
    	
    	//Jokers
    	for (int i = 0; i < 8; i++) {
    		deck.add(new MahjongTile("Textures/joker.png"));
    	}
    	
    	//shuffle the deck after construction
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
    	
    	//hand stuff
    	float spacing = 1.5f;
    	float startX = -2 * spacing;
    	
    	ArrayList<MahjongTile> hand = drawHand(5);
    

    	for (int i = 0; i < hand.size(); i++) {

    	    // Load one copy of the model
    	    Spatial tile = assetManager.loadModel("Models/tile.glb");

    	    // Load this tile's texture
    	    Texture texture = assetManager.loadTexture(hand.get(i).getTexturePath());

    	    // Create a material
    	    Material mat = new Material(assetManager,
    	            "Common/MatDefs/Misc/Unshaded.j3md");
    	    mat.setTexture("ColorMap", texture);

    	    // Apply it to the model
    	    tile.setMaterial(mat);
    	    
    	    // set scale
    	    tile.setLocalScale(1f);
    	    
    	    // Space the tiles apart
    	    tile.setLocalTranslation(startX + i * spacing, 0, 0);
    	    
    	    // Tilt toward the camera
    	    tile.rotate((float) Math.toRadians(30), 0, 0);

    	    // Add to the scene
    	    rootNode.attachChild(tile);
    	   
    	}
        
        cam.setLocation(new com.jme3.math.Vector3f(0, 2, 8));
        cam.lookAt(new com.jme3.math.Vector3f(0, 0, 0), com.jme3.math.Vector3f.UNIT_Y);
    }

}
