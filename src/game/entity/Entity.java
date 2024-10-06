package game.entity;

import game.core.MovementHandler;
import game.core.MovementHandler.Direction;
import game.graphics.GameHud;
import game.object.GameObjectsManager;
import game.object.GameObject;
import game.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static game.graphics.Screen.SCALE;

public abstract class Entity {
    public static final int SPRITE_RELOAD_RATE = 40;
    public static final int ENTITY_DOWNSIZE = 10;
    private WorldLocation position;
    private final MovementHandler movementHandler;
    private int spriteReloadCounter = 0;
    private int spriteIdx = 0;
    private Rectangle solidArea;

    public Entity(MovementHandler movementHandler) {
        this.position = getStartingWorldPosition();
        this.movementHandler = movementHandler;
    }

    public abstract int getSpeed();
    public abstract WorldLocation getStartingWorldPosition();
    public abstract WorldLocation getScreenPosition();
    public abstract List<BufferedImage> getUpImageSequence();
    public abstract List<BufferedImage> getDownImageSequence();
    public abstract List<BufferedImage> getLeftImageSequence();
    public abstract List<BufferedImage> getRightImageSequence();
    public abstract List<BufferedImage> getIdleImageSequence();

    public void update(GameObjectsManager gameObjectsManager, GameHud gameHud, boolean tileCollision, GameObject objectOfCollision) {
        if (tileCollision) return;
        if (objectOfCollision != null) {
            objectOfCollision.handleCollisionEvent(this, gameHud, gameObjectsManager);
            if (objectOfCollision.isSolid()) {
                return;
            }
        }
       if (movementHandler.isMoving()) {
           move();
           changeSprite();
       }
    }

    public WorldLocation getWorldPosition() {
        return position;
    }

    public Direction getDirection() {
        return movementHandler.getDirection();
    }

    public Rectangle getEntityCollisionBox() {
        return new Rectangle(getWorldPosition().x() + getSolidArea().x,getWorldPosition().y() + getSolidArea().y, getSolidArea().width , getSolidArea().height
        );
    }

    private Rectangle getSolidArea()
    {
        if (solidArea == null) {
            var image = getIdleImageSequence().getFirst(); //just one random image
            //udelame postavicku o trochu mensi nez tile abys se snadno vesla do uzkych ulicek
            solidArea = new Rectangle(0, 0, image.getWidth() * SCALE - ENTITY_DOWNSIZE, image.getHeight() * SCALE - ENTITY_DOWNSIZE);
        }
        return solidArea;
    }

    public boolean isMoving() {
        return movementHandler.isMoving();
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = getSpriteSequence(movementHandler.getDirection()).get(spriteIdx);
        g2.drawImage(image, getScreenPosition().x(), getScreenPosition().y(), getSolidArea().width,  getSolidArea().height, null);
    }

    private void changeSprite()
    {
        // zvysime rychlost stridani framu s rychlosti pohybu: proto delime rychlosti
        if (spriteReloadCounter++ < SPRITE_RELOAD_RATE / getSpeed()) return;
        if (++spriteIdx >= getSpriteSequence(movementHandler.getDirection()).size()) {
            spriteIdx = 0;
        }
        spriteReloadCounter = 0;
    }

    private void move()
    {
        position = switch (movementHandler.getDirection()) {
            case UP ->    position.moveY(-getSpeed());
            case DOWN ->  position.moveY(getSpeed());
            case LEFT ->  position.moveX(-getSpeed());
            case RIGHT -> position.moveX(getSpeed());
            case IDLE ->  position;
        };
    }

    private List<BufferedImage> getSpriteSequence(Direction direction) {
        return switch (direction) {
            case LEFT -> getLeftImageSequence();
            case RIGHT -> getRightImageSequence();
            case UP -> getUpImageSequence();
            case DOWN -> getDownImageSequence();
            case IDLE -> getIdleImageSequence();
        };
    }
}
