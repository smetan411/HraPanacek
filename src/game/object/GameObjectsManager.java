package game.object;

import game.entity.Player;
import game.world.WorldLocation;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GameObjectsManager {
    private final Set<GameObject> gameObjects = new HashSet<>();

    public GameObjectsManager() {
        gameObjects.addAll(List.of(
                new Key(WorldLocation.tiles(37, 6)),
                new Key(WorldLocation.tiles(19, 22)),
                new Key(WorldLocation.tiles(34, 40)),
                new Door(WorldLocation.tiles(19, 18)),
                new Door(WorldLocation.tiles(15, 24)),
                new Door(WorldLocation.tiles(8, 18)),
                new Chest(WorldLocation.tiles(11, 16)),
                new Boots(WorldLocation.tiles(18,32))
        ));
    }


    public void removeObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public List<GameObject> getAllGameObjects() {
        return List.copyOf(gameObjects);
    }

    public void drawAll(Graphics2D g2D, Player player) {
        gameObjects.forEach( gameObject -> gameObject.draw(g2D, player));
    }
}
