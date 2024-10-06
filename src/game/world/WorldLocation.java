package game.world;

import java.awt.*;

import static game.graphics.Screen.TILE_SIZE;

public record WorldLocation(int x, int y) {

    public WorldLocation moveX(int value) {
        return new WorldLocation(x + value, y);
    }

    public WorldLocation moveY(int value) {
        return new WorldLocation(x, y + value);
    }

    public WorldLocation minus(WorldLocation worldLocation) {
        return new WorldLocation(x - worldLocation.x, y - worldLocation.y);
    }

    public WorldLocation plus(WorldLocation worldLocation) {
        return new WorldLocation(x + worldLocation.x, y + worldLocation.y);
    }

    public Point point() {
        return new Point(x, y);
    }

    public static WorldLocation tiles(int tilesRow, int tilesCol) {
        return new WorldLocation(tilesRow * TILE_SIZE, tilesCol * TILE_SIZE);
    }


}
