package game.core;

import game.entity.Entity;
import game.object.GameObjectsManager;
import game.object.GameObject;
import game.world.WorldLocation;
import game.world.WorldMap;

import java.awt.*;

public final class CollisionChecker {

    private final WorldMap worldMap;
    private final GameObjectsManager gameObjectsManager;

    public CollisionChecker(WorldMap worldMap, GameObjectsManager gameObjectsManager) {
        this.worldMap = worldMap;
        this.gameObjectsManager = gameObjectsManager;
    }

    public boolean checkTiles(Entity entity) {
        if (!entity.isMoving()) return false;
        Rectangle entityCollisionArea = entity.getEntityCollisionBox();

        int entityLeft = (int) entityCollisionArea.getMinX();
        int entityRight = (int) entityCollisionArea.getMaxX();
        int entityTop = (int) entityCollisionArea.getMinY();
        int entityBottom = (int) entityCollisionArea.getMaxY();

        switch (entity.getDirection()) {
            case UP -> {
                entityTop -= entity.getSpeed();
                var tile1 = worldMap.getTile( new WorldLocation(entityLeft, entityTop));
                var tile2 = worldMap.getTile( new WorldLocation(entityRight, entityTop));
                if (tile1.isSolid() || tile2.isSolid()) {
                    return true;
                }
            }
            case DOWN -> {
                entityBottom += entity.getSpeed();
                var tile1 = worldMap.getTile(new WorldLocation(entityLeft, entityBottom));
                var tile2 = worldMap.getTile(new WorldLocation(entityRight, entityBottom));
                if (tile1.isSolid() || tile2.isSolid()) {
                    return true;
                }
            }
            case LEFT -> {
                entityLeft -= entity.getSpeed();
                var tile1 = worldMap.getTile(new WorldLocation(entityLeft, entityTop));
                var tile2 = worldMap.getTile(new WorldLocation(entityLeft, entityBottom));
                if (tile1.isSolid() || tile2.isSolid()) {
                    return true;
                }
            }
            case RIGHT -> {
                entityRight += entity.getSpeed();
                var tile1 = worldMap.getTile(new WorldLocation(entityRight, entityTop));
                var tile2 = worldMap.getTile(new WorldLocation(entityRight, entityBottom));
                if (tile1.isSolid() || tile2.isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public GameObject checkGameObjects(Entity entity) {
        for (GameObject gameObject : gameObjectsManager.getAllGameObjects()) {
            Rectangle entityCollisionArea =  entity.getEntityCollisionBox();
            Rectangle gameObjectCollisionArea = new Rectangle(gameObject.getPosition().x() + gameObject.getSolidArea().x, gameObject.getPosition().y() + gameObject.getSolidArea().y, gameObject.getSolidArea().width, gameObject.getSolidArea().height);

            switch (entity.getDirection()) {
                case UP -> entityCollisionArea.y -= entity.getSpeed();
                case DOWN -> entityCollisionArea.y += entity.getSpeed();
                case LEFT -> entityCollisionArea.x -= entity.getSpeed();
                case RIGHT -> entityCollisionArea.x += entity.getSpeed();
            }
            if (entityCollisionArea.intersects(gameObjectCollisionArea)) {
                return gameObject;
            }
        }
        return null;
    }

}
