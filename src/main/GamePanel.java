package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 16; //16x16 pixel, základní velikost spritů
    final int scale = 3; // zvětšení spritů třikrát
    public final int tileSize = originalTileSize * scale; //48x48 pixel
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;// 48x16=768 pixels
    public final int screenHeight = tileSize * maxScreenRow;// 48*12=576 pixels

    // world map parameters
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    // FPS
    int FPS = 60;
    // System
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();  //se sound efect

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //Entity and object
    public Player player = new Player(this, keyH); // GamePanel-this
    public SuperObject obj[] = new SuperObject[10];// zadáváme, že současně zobrazíme 10 objektů


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();//automaticky zavola metodu run()
    }

  @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;//=1sec/60, prekreslujeme 60x za vterinu
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
//            long currentTime = System.nanoTime();//1 sec = 10^9 nanoseconds
//            System.out.println("Current time : " + currentTime);
//            System.out.println("Hra běží.");
            update();
            repaint();
//po provedeni update a repaint potrebujeme znat, jak dlouho bude system spat
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                //na radku remainingTime = remainingTime / 1000000; prevadime cas v nano- na milisekundy kvuli sleep
                //najedte kurzorem na .sleep a v popisu to uvidite
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval; // po probehnuti sleep nastavime znovu drawInterval
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
       player.update(); //sem zavolame metodu ze tridy Player
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // menime graphics na 2d, ma vice funkci

        // tile
        tileM.draw(g2);

        // object
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        // player
        player.draw(g2);// sem zavolame metodu ze tridy Player
        // player
        ui.draw(g2);
        g2.dispose();  //usetri pamet
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }
   //SE sound effect
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}