package views;

import models.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileView {
    private Tile tile;
    private Point position;
    private BufferedImage image;

    public TileView(Tile tile, BufferedImage image, Point position) {
        this.tile = tile;
        this.position = position;
        this.image = image;

    }

    public Point getPosition() {
        return position;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
