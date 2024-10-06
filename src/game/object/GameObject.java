package game.object;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.GameHud;
import game.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

import static game.graphics.Screen.*;

public abstract class GameObject {
    private final BufferedImage image;
    private final WorldLocation position;

    public GameObject(String pathToImage, WorldLocation position)
    {
        this.image = readImage(pathToImage);
        this.position = position;
    }

    public abstract boolean isSolid();
    public abstract void handleCollisionEvent(Entity entity, GameHud gameHud, GameObjectsManager gameObjectsManager);

    public BufferedImage getImage()
    {
        return image;
    }

    public WorldLocation getPosition()
    {
        return position;
    }

    public Rectangle getSolidArea()
    {
        return new Rectangle(0, 0, image.getWidth() * SCALE  - Entity.ENTITY_DOWNSIZE, image.getHeight() * SCALE - Entity.ENTITY_DOWNSIZE);
    }

    //delegate to drawing utility
    public void draw(Graphics2D g2, Player player){

        if(isOnScreen(position, player.getWorldPosition()) ) {
            WorldLocation screenLocation = position.minus(player.getWorldPosition()).plus(player.getScreenPosition());
            g2.drawImage(image, screenLocation.x() , screenLocation.y(), getSolidArea().width, getSolidArea().height, null );
        }
    }
}
