package game.world;

import game.graphics.Screen;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static game.graphics.Screen.SCREEN_CENTER;
import static game.graphics.Screen.TILE_SIZE;

public final class World {
    private final TileManager tileManager = new TileManager();
    private final WorldMap worldMap;

    public World() {
        worldMap = loadMap("/maps/world01.txt");
    }

    public WorldMap getWorldMap()
    {
        return worldMap;
    }

    private WorldMap loadMap(String pathToMap) {
        try {
            WorldMap worldMap = new WorldMap();
            List<String> linesFromMapFile = Files.readAllLines(Path.of(getClass().getResource(pathToMap).toURI()));
            for (String mapLine : linesFromMapFile) {
                List<Tile> lineTiles = Arrays.stream(mapLine.split(" "))
                        .map(tileManager::getTile)
                        .toList();
                worldMap.addRowOfTiles(lineTiles);
            }
            return worldMap;
        } catch (Exception e) {
            throw new RuntimeException("Can not load map. Path: " + pathToMap, e);
        }
    }

    public void draw(Graphics2D g2, WorldLocation worldLocation) {

        for (int rowIdx = 0; rowIdx < worldMap.rowsCount(); rowIdx++) {
            List<Tile> worldMapRow = worldMap.getRow(rowIdx);
            for (int colIdx = 0; colIdx < worldMapRow.size(); colIdx++) {
                WorldLocation tileLocation = new WorldLocation(colIdx * TILE_SIZE, rowIdx * TILE_SIZE);
                if (Screen.isOnScreen(tileLocation, worldLocation)) {
                    Tile tile = worldMap.getTile(rowIdx, colIdx);
                    if (tile == null ) return;
                    WorldLocation tileScreenLocation = tileLocation.minus(worldLocation).plus(SCREEN_CENTER);
                    g2.drawImage(tile.getImage(), tileScreenLocation.x(), tileScreenLocation.y(), TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }
}
