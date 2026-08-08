package cowboymahjong.game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import java.util.HashMap;
import java.util.Map;

// Builder pattern implementation for making tile spatials
// Makes tile assembly simpler by putting texture/positioning/scaling/tagging etc
// lets the caller configure the properties it needs with chained method calls
//puts off object creation until build() is called

public class TileBuilder {

    private final AssetManager assetManager;

    private String modelPath;
    private String texturePath;
    private Vector3f position = new Vector3f(0, 0, 0);
    private float scale = 1f;
    private float rotationXDegrees = 0f;
    private Map<String, Object> userDataMap = new HashMap<>();

    public TileBuilder(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public TileBuilder modelPath(String modelPath) {
        this.modelPath = modelPath;
        return this;
    }

    public TileBuilder texture(String texturePath) {
        this.texturePath = texturePath;
        return this;
    }

    public TileBuilder position(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
        return this;
    }

    public TileBuilder scale(float scale) {
        this.scale = scale;
        return this;
    }

    public TileBuilder rotateX(float degrees) {
        this.rotationXDegrees = degrees;
        return this;
    }

    // Can be called multiple times to attach multiple key/value pairs.
    public TileBuilder userData(String key, Object value) {
        this.userDataMap.put(key, value);
        return this;
    }

    public Spatial build() {
        if (modelPath == null) {
            throw new IllegalStateException("modelPath must be set before building a tile");
        }

        Spatial tile = assetManager.loadModel(modelPath);

        if (texturePath != null) {
            Texture texture = assetManager.loadTexture(texturePath);
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setTexture("ColorMap", texture);
            tile.setMaterial(mat);
        }

        tile.setLocalScale(scale);
        tile.setLocalTranslation(position);

        if (rotationXDegrees != 0f) {
            tile.rotate((float) Math.toRadians(rotationXDegrees), 0, 0);
        }

        for (Map.Entry<String, Object> entry : userDataMap.entrySet()) {
            tile.setUserData(entry.getKey(), entry.getValue());
        }

        return tile;
    }
}