package game.world;

import java.util.HashMap;
import java.util.Map;

final class TileManager {

    private final Map<String, Tile> tiles = new HashMap<>();


    TileManager() {
        tiles.put("g", new Tile("/tiles/grass.png"));
        tiles.put("a", new Tile("/tiles/wall.png", true));
        tiles.put("w", new Tile("/tiles/water.png", true));
        tiles.put("e", new Tile("/tiles/earth.png"));
        tiles.put("t", new Tile("/tiles/tree.png", true));
        tiles.put("s", new Tile("/tiles/sand.png"));
    }

    Tile getTile(String tileShortcut) {
        return tiles.get(tileShortcut);
    }
}
