package game.graphics;

import game.object.GameObject;
import game.world.WorldLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Screen {
    public static final int SCALE = 3; // zvětšení spritů třikrát
    public static final int ORIGINAL_TILE_SIZE = 16; //16x16 pixel, základní velikost tilu
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //48x48 pixel
    //game.world related parameters
    public static final int SCREEN_WIDTH_TILES = 16;
    public static final int SCREEN_HEIGHT_TILES = 12;
    public static final Rectangle SCREEN = new Rectangle(TILE_SIZE * SCREEN_WIDTH_TILES, TILE_SIZE * SCREEN_HEIGHT_TILES);
    public static final WorldLocation SCREEN_CENTER = new WorldLocation(SCREEN.width / 2 - (TILE_SIZE / 2), SCREEN.height / 2 - (TILE_SIZE / 2));


    public static boolean isOnScreen(WorldLocation objectLocation, WorldLocation worldLocation)
    {
        Dimension visibleScreenSize = visibleScreenSize(SCREEN.getSize());
        Rectangle visibleArea = new Rectangle(leftUpperCornerFromCenter(visibleScreenSize, worldLocation), visibleScreenSize);
        return visibleArea.contains(objectLocation.point());
    }

    private static Dimension visibleScreenSize(Dimension d) {
        return new Dimension(d.width + TILE_SIZE, d.height + TILE_SIZE);
    }

    private static Point leftUpperCornerFromCenter(Dimension rectangleSize, WorldLocation center) {
        return new Point(center.x() - rectangleSize.width / 2, center.y() - rectangleSize.height / 2);
    }

    public static BufferedImage readImage(String path)
    {
        try (var stream = GameObject.class.getResourceAsStream(path))
        {
            if (stream == null) throw new IOException();
            return ImageIO.read(stream);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Can not load image: " + path, e);
        }
    }

    public static List<BufferedImage> readImages(String... paths) {
        List<BufferedImage> images = new ArrayList<>();
        for (String path : paths) {
            images.add(readImage(path));
        }
        return images;
    }
}
