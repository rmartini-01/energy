package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TilesFactory {

    public static final  HashMap<String, BufferedImage> squareGrayTiles = createGraySquareTiles();
    public static final  HashMap<String, BufferedImage> hexaGrayTiles = createGrayHexagoneTiles();
    public static final  HashMap<String, BufferedImage> squareWhiteTiles = createWhiteSquareTiles();
    public static final  HashMap<String, BufferedImage> hexaWhiteTiles = createWhiteHexagoneTiles();


    protected static HashMap<String, BufferedImage> createGraySquareTiles() {
        BufferedImage gridImage = null;
        HashMap<String, BufferedImage> grayImages = new HashMap<>();
        try {
            gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage square = gridImage.getSubimage(0, 0, 120, 120);
            BufferedImage wifi = gridImage.getSubimage(120, 120, 120, 120);
            BufferedImage lamp = gridImage.getSubimage(240, 120, 120, 120);
            BufferedImage connection = gridImage.getSubimage(0, 240, 120, 120);
            BufferedImage curve = gridImage.getSubimage(120, 240, 120, 120);
            BufferedImage line = gridImage.getSubimage(240, 240, 120, 120);
            // BufferedImage source = gridImage.getSubimage(0, 480, 120, 120);

            grayImages.put("square", square);
            grayImages.put("wifi", wifi);
            grayImages.put("lamp", lamp);
            grayImages.put("connection", connection);
            grayImages.put("curve", curve);
            grayImages.put("line", line);
            //grayImages.put("source", source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grayImages;
    }

    protected static HashMap<String, BufferedImage> createWhiteSquareTiles() {
        BufferedImage gridImage = null;
        HashMap<String, BufferedImage> grayImages = new HashMap<>();
        try {
            gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage square = gridImage.getSubimage(0, 0+ 3*120, 120, 120);
            BufferedImage wifi = gridImage.getSubimage(120 , 120+ 3*120, 120, 120);
            BufferedImage lamp = gridImage.getSubimage(240, 120+ 3*120, 120, 120);
            BufferedImage connection = gridImage.getSubimage(0, 240+ 3*120, 120, 120);
            BufferedImage curve = gridImage.getSubimage(120, 240+ 3*120, 120, 120);
            BufferedImage line = gridImage.getSubimage(240, 240+ 3*120, 120, 120);
            BufferedImage source = gridImage.getSubimage(0, 480, 120, 120);

            grayImages.put("square", square);
            grayImages.put("wifi", wifi);
            grayImages.put("lamp", lamp);
            grayImages.put("connection", connection);
            grayImages.put("curve", curve);
            grayImages.put("line", line);
            grayImages.put("source", source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grayImages;
    }

    protected static HashMap<String, BufferedImage> createGrayHexagoneTiles() {

        HashMap<String, BufferedImage> grayImages = new HashMap<>();
        try {
            BufferedImage gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage hexagone = gridImage.getSubimage(360, 0, 120, 104);
            BufferedImage wifi = gridImage.getSubimage(480, 120, 120, 104);
            BufferedImage lamp = gridImage.getSubimage(600, 120, 120, 104);

            BufferedImage connection = gridImage.getSubimage(360, 240, 120, 104);
            BufferedImage curve = gridImage.getSubimage(480, 240, 120, 104);
            BufferedImage large_curve = gridImage.getSubimage(600, 240, 120, 104);
            BufferedImage line = gridImage.getSubimage(720, 240, 120, 104);
            //BufferedImage source = gridImage.getSubimage(360, 480, 120, 104);

            grayImages.put("hexagone", hexagone);
            grayImages.put("wifi", wifi);
            grayImages.put("lamp", lamp);
            grayImages.put("connection", connection);
            grayImages.put("curve", curve);
            grayImages.put("large_curve", large_curve);
            grayImages.put("line", line);
            // grayImages.put("source", source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grayImages;
    }

    private static HashMap<String, BufferedImage> createWhiteHexagoneTiles() {

        HashMap<String, BufferedImage> grayImages = new HashMap<>();
        try {
            BufferedImage gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage hexagone = gridImage.getSubimage(360, 0+ 3*120, 120, 104);
            BufferedImage wifi = gridImage.getSubimage(480, 120 + 3*120, 120, 104);
            BufferedImage lamp = gridImage.getSubimage(600, 120+ 3*120, 120, 104);

            BufferedImage connection = gridImage.getSubimage(360, 240 + 3*120, 120, 104);
            BufferedImage curve = gridImage.getSubimage(480, 240+ 3*120, 120, 104);
            BufferedImage large_curve = gridImage.getSubimage(600, 240 + 3*120, 120, 104);
            BufferedImage line = gridImage.getSubimage(720, 240+ 3*120, 120, 104);
            BufferedImage source = gridImage.getSubimage(360, 480, 120, 104);

            grayImages.put("hexagone", hexagone);
            grayImages.put("wifi", wifi);
            grayImages.put("lamp", lamp);
            grayImages.put("connection", connection);
            grayImages.put("curve", curve);
            grayImages.put("large_curve", large_curve);
            grayImages.put("line", line);
            grayImages.put("source", source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grayImages;
    }
}
