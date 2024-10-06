package game.graphics;

import game.entity.Player;
import game.object.Key;
import game.world.WorldLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static game.graphics.Screen.SCREEN;
import static game.graphics.Screen.TILE_SIZE;

// UI user interface
public final class GameHud {
    private static final Font ARIAL_40 = new Font("Arial", Font.PLAIN, 40); // B jako bold, tučný
    private static final Font ARIAL_80B = new Font("Arial", Font.BOLD, 80); // B jako bold, tučný
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private final Player player;
    private final BufferedImage keyImage;
    private final double startTime = System.currentTimeMillis();
    private final Runnable stopGameCallback;

    private int messageDisplayDuration = 0;
    private String message;
    private boolean gameFinished = false;

    public GameHud(Player player, Runnable stopGameCallback) {
        this.player = player;
        this.stopGameCallback = stopGameCallback;
        keyImage = new Key(WorldLocation.tiles(0, 0)).getImage();
    }

    public void showMessage(String text) {
        message = text;
        messageDisplayDuration = 100;
    }

    public void stopGame() {
        gameFinished = true;
    }

    public void draw(Graphics2D g2) {
        String time = DECIMAL_FORMAT.format((System.currentTimeMillis() - startTime) / 1000);
        if (gameFinished) {
            drawText(g2,"You found the treasure!", -2,  ARIAL_40, Color.WHITE);
            drawText(g2, "Your time is :" + time + "!", 0, ARIAL_40, Color.WHITE);
            drawText(g2, "Congratulations!", 2, ARIAL_80B, Color.YELLOW);
            stopGameCallback.run();
        } else {
            //klice
            int statusRow = (int)(1.5 * TILE_SIZE);
            g2.setFont(ARIAL_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, TILE_SIZE / 2 , TILE_SIZE / 2, TILE_SIZE, TILE_SIZE, null);
            g2.drawString("x " + player.getKeyCount(), (int)(1.5 * TILE_SIZE), statusRow);
            //time
            g2.drawString("Time:" + time, SCREEN.width - TILE_SIZE * 5, statusRow);
            //message
            if (--messageDisplayDuration > 0) {
                drawText(g2, message, 1, g2.getFont().deriveFont(30F), Color.WHITE);
            }
        }
    }

    private void drawText(Graphics2D g2d, String text, int position, Font font, Color color) {
        g2d.setColor(color);
        g2d.setFont(font);
        int textLength = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = SCREEN.width / 2 - textLength / 2; //tim je text vycentrovany
        int y = SCREEN.height / 2 + TILE_SIZE * position; //je pod panackem
        g2d.drawString(text, x, y);
    }

}
