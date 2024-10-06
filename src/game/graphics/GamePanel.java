package game.graphics;

import game.core.MovementHandler;
import game.core.CollisionChecker;
import game.entity.Player;
import game.object.GameObjectsManager;
import game.world.World;

import javax.swing.*;
import java.awt.*;

import static game.graphics.Screen.SCREEN;

public final class GamePanel extends JPanel {
    private final GameObjectsManager gameObjectsManager;
    private final Player player;
    private final GameHud gameHud;
    private final World world;
    private final CollisionChecker collisionChecker;

    public GamePanel(MovementHandler movementHandler, GameObjectsManager gameObjectsManager, CollisionChecker collisionChecker, World world, Player player, GameHud gameHud) {
        this.gameObjectsManager = gameObjectsManager;
        this.player = player;
        this.gameHud = gameHud;
        this.world = world;
        this.collisionChecker = collisionChecker;

        this.setPreferredSize(SCREEN.getSize());
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(movementHandler);
        this.setFocusable(true);
    }

    public void draw() {
        player.update(gameObjectsManager, gameHud, collisionChecker.checkTiles(player), collisionChecker.checkGameObjects(player));
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        world.draw(g2D, player.getWorldPosition());
        gameObjectsManager.drawAll(g2D, player);
        player.draw(g2D);
        gameHud.draw(g2D);
        g2D.dispose();
    }
}