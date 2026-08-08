package cowboymahjong.game;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class RisingTile {
    private final Spatial spatial;
    private final float startY;
    private final float endY;
    private final float duration;
    private float elapsed = 0f;

    public RisingTile(Spatial spatial, float startY, float endY, float duration) {
        this.spatial = spatial;
        this.startY = startY;
        this.endY = endY;
        this.duration = duration;
    }

    public boolean update(float tpf) {
        elapsed += tpf;
        float t = Math.min(elapsed / duration, 1f);
        float y = startY + (endY - startY) * t;

        Vector3f pos = spatial.getLocalTranslation();
        spatial.setLocalTranslation(pos.x, y, pos.z);

        return t >= 1f;
    }

    public Spatial getSpatial() {
        return spatial;
    }
}