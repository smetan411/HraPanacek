package game.entity;

import game.core.MovementHandler;
import game.graphics.Screen;
import game.world.WorldLocation;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static game.core.MovementHandler.Direction.*;

public final class Player extends Entity {
    private int keyCount = 0;
    private int additionalSpeed = 0;
    private Map<MovementHandler.Direction, List<BufferedImage>> movementImages;

    public Player(MovementHandler movementHandler) {
        super(movementHandler);
        loadMovementSet();
    }

    private void loadMovementSet() {
        movementImages = Map.of(
                RIGHT, Screen.readImages("/player/boy_right_1.png", "/player/boy_right_2.png"),
                LEFT, Screen.readImages("/player/boy_left_1.png", "/player/boy_left_2.png"),
                DOWN, Screen.readImages("/player/boy_down_1.png", "/player/boy_down_2.png"),
                UP, Screen.readImages("/player/boy_up_1.png", "/player/boy_up_2.png"),
                IDLE, Screen.readImages("/player/boy_left_1.png", "/player/boy_right_1.png")
        );
    }

    @Override
    public WorldLocation getScreenPosition() {
        //player is always in the middle of the screen
        return Screen.SCREEN_CENTER;
    }

    @Override
    public int getSpeed() {
        return 4 + additionalSpeed;
    }

    public void speedUp(int value) {
        additionalSpeed += value;
    }

    public void pickUpKey() {
        keyCount++;
    }

    public void useKey() {
        keyCount--;
    }

    public int getKeyCount() {
        return keyCount;
    }

    @Override
    public WorldLocation getStartingWorldPosition() {
        return WorldLocation.tiles(12, 7);
    }

    @Override
    public List<BufferedImage> getUpImageSequence() {
        return movementImages.get(UP);
    }

    @Override
    public List<BufferedImage> getDownImageSequence() {
        return movementImages.get(DOWN);
    }

    @Override
    public List<BufferedImage> getLeftImageSequence() {
        return movementImages.get(LEFT);
    }

    @Override
    public List<BufferedImage> getRightImageSequence() {
        return movementImages.get(RIGHT);
    }

    @Override
    public List<BufferedImage> getIdleImageSequence() {
        return movementImages.get(IDLE);
    }
}
