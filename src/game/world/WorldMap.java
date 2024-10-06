package game.world;

import java.util.ArrayList;
import java.util.List;

import static game.graphics.Screen.TILE_SIZE;

public final class WorldMap {
    private final List<List<Tile>> map = new ArrayList<>();

    void addRowOfTiles(List<Tile> tiles) {
        map.add(tiles);
    }

    public Tile getTile(int row, int col) {
        return map.get(row).get(col);
    }

    public Tile getTile(WorldLocation location) {
        return getTile(location.y() / TILE_SIZE, location.x() / TILE_SIZE);
    }

    int rowsCount() {
        return map.size();
    }

    List<Tile> getRow(int index) {
        return map.get(index);
    }
}
