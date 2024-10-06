package game.world;

import game.graphics.Screen;

import java.awt.image.BufferedImage;

public final class Tile {

    private final BufferedImage image;
    private final boolean solid;

    public Tile(String pathToImage, boolean solid) {
        this.image = Screen.readImage(pathToImage);
        this.solid = solid;
    }

    public Tile(String pathToImage) {
        this(pathToImage, false);
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public boolean isSolid()
    {
        return solid;
    }
}
