package game.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static game.core.MovementHandler.Direction.*;
import static java.awt.event.KeyEvent.*;

public final class MovementHandler implements KeyListener {
    private Direction movementDirection = IDLE;
    private final Set<Integer> keysDown = new HashSet<>();
    private final static Map<Integer, Direction> DIRECTION_TO_KEY_MAPPING = Map.of(
            VK_A,         LEFT,
            VK_KP_LEFT,   LEFT,
            VK_D,         RIGHT,
            VK_KP_RIGHT,  RIGHT,
            VK_S,         DOWN,
            VK_KP_DOWN,   DOWN,
            VK_W,         UP,
            VK_KP_UP,     UP
    );

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    public Direction getDirection()
    {
        return movementDirection;
    }

    public boolean isMoving() {
        return !keysDown.isEmpty();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(e.getKeyCode());
        movementDirection = DIRECTION_TO_KEY_MAPPING.getOrDefault(e.getKeyCode(), IDLE);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysDown.remove(e.getKeyCode());
        Optional<Integer> pressedKey = keysDown.stream().findFirst();
        if (pressedKey.isPresent()) {
            movementDirection = DIRECTION_TO_KEY_MAPPING.getOrDefault(pressedKey.get(), IDLE);
        }
    }
}
