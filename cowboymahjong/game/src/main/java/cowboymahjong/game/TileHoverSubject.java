package cowboymahjong.game;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

public class TileHoverSubject {

    private final List<TileHoverListener> listeners = new ArrayList<>();
    private Spatial currentHover = null;

    public void addListener(TileHoverListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TileHoverListener listener) {
        listeners.remove(listener);
    }

    public void updateHover(Spatial newHover) {
        if (newHover != currentHover) {
            Spatial previous = currentHover;
            currentHover = newHover;
            notifyListeners(previous, newHover);
        }
    }

    private void notifyListeners(Spatial previous, Spatial newTile) {
        for (TileHoverListener listener : listeners) {
            listener.onTileHoverChanged(previous, newTile);
        }
    }
}