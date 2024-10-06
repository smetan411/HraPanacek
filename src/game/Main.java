package game;

import game.core.MovementHandler;
import game.core.Sound;
import game.core.CollisionChecker;
import game.entity.Player;
import game.graphics.GameHud;
import game.graphics.GamePanel;
import game.object.GameObjectsManager;
import game.world.World;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Main implements Runnable {
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final Sound music = new Sound("/sound/BlueBoyAdventure.wav");

    //now dependencies between classes
    private final MovementHandler movementHandler = new MovementHandler();
    private final GameObjectsManager gameObjectsManager = new GameObjectsManager();
    private final World world = new World();
    private final Player player = new Player(movementHandler);
    private final CollisionChecker collisionChecker = new CollisionChecker(world.getWorldMap(), gameObjectsManager);
    private final GameHud gameHud = new GameHud(player, this::stopGame);
    private final GamePanel gamePanel = new GamePanel(movementHandler, gameObjectsManager, collisionChecker, world, player, gameHud);

    @Override
    public void run() {
        gamePanel.draw();
    }

    public void startGame() {
        music.playInLoop();
        openGameWindow();
        //kazdych 20ms prekreslime scenu tj FPS je 50, v teto hre je rychlost pohybu zavisla na rychlosti prekreslovani sceny
        //v komercnich hrach to tak neni, nicmene to by hru zkomplikovalo
        //jinak by bylo nutne mit dva executory jeden na prekreslovani a druhy na update pohybu a snimani klavesnice
        scheduledExecutorService.scheduleAtFixedRate(this, 0, 20, TimeUnit.MILLISECONDS);
    }

    void stopGame() {
        music.stop();
        scheduledExecutorService.shutdownNow();
    }

    private void openGameWindow() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startGame();
    }
}
