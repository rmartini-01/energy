package views;

import listeners.NavigateBackListener;
import models.Board;
import models.Level;
import models.Observer;
import models.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public abstract class BoardAbstract extends JPanel implements Observer {
    protected Graphics2D g2d;
    protected ArrayList<TileView> tileViews;
    protected Level level;
    protected Board board;
    protected HashMap<String, BufferedImage> squareGrayTiles = createGraySquareTiles();
    protected HashMap<String, BufferedImage> hexaGrayTiles = createGrayHexagoneTiles();
    protected HashMap<String, BufferedImage> squareWhiteTiles = createWhiteSquareTiles();
    protected HashMap<String, BufferedImage> hexaWhiteTiles = createWhiteHexagoneTiles();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        Point origin = new Point(80, 70);
        if (tileViews.size() == 0) { //premier lancement
            createBoard(origin);
        } else {
            updateBoard(origin);

        }
    }
    protected void createBoard(Point origin){
        if (!board.isSquare()) {
            createHexagoneBoard(g2d, origin);
        } else {
            createSquareBoard(g2d, origin);
        }
    }

    public Level getLevel() {
        return level;
    }


    public boolean contains(Point tilePosition, Point mousePosition, int width, int height) {
        return tilePosition.x <= mousePosition.x && mousePosition.x <= tilePosition.x + width
                && tilePosition.y <= mousePosition.y && mousePosition.y <= tilePosition.y + height;
    }

    protected void createSquareBoard(Graphics2D g2d, Point origin) {
        tileViews.clear();
        for (int i = 0; i < board.getBoard().size(); i++) {
            Tile tile = board.getBoard().get(i);
            tileViews.addAll(drawSquareTiles(origin, tile));
        }
    }

    protected void updateBoard(Point origin){
        ArrayList<TileView> newTileView = new ArrayList<>();
        if (board.getShape() == 'H') {
            for (Tile tile : board.getBoard()) {
                newTileView.addAll(drawHexaTiles(origin, tile));
            }
        } else {
            for (Tile tile : board.getBoard()) {
                newTileView.addAll(drawSquareTiles(origin, tile));
            }
        }
        tileViews.clear();
        tileViews.addAll(newTileView);
    }

    protected void createHexagoneBoard(Graphics2D g2d, Point origin) {
        tileViews.clear();
        for (int i = 0; i < board.getBoard().size(); i++) {
            Tile tile = board.getBoard().get(i);
            tileViews.addAll(drawHexaTiles(origin, tile));
        }
    }

    protected ArrayList<TileView> drawHexaTiles(Point origin, Tile tile) {
        int posX = tile.getPositionX();
        int posY = tile.getPositionY();
        int x = origin.x + (posX * 104) - posX * 15;
        int y = origin.y + (posY * 104) + (posX%2==0 ? 0 : 52);
        String role = tile.getRole().toString().toLowerCase();
        HashMap<String, BufferedImage> tiles = hexaGrayTiles;
        if(tile.getLit()){
            tiles = hexaWhiteTiles;
        }
        BufferedImage hexaImage = tiles.get("hexagone");
        BufferedImage connection = tiles.get("connection");
        BufferedImage image;
        ArrayList<TileView> temporaryTileViews = new ArrayList<>();
        temporaryTileViews.add(new TileView(tile, hexaImage, new Point(x, y)));
        g2d.drawImage(hexaImage, x, y, null);
        if (!role.equalsIgnoreCase("empty")) {
            image = tiles.get(role);
            for (int e = 0; e < tile.getEdges().size(); e++) {
                g2d.drawImage(rotateImage(connection,
                                (tile.getEdges().get(e) * 60) ),
                        x, y, null);
            }
            g2d.drawImage(image, x, y, null);
        } else {

            if (tile.getEdges().size() != 0) {
                ArrayList<Integer> edges = tile.getEdges();
                BufferedImage line = tiles.get("line");
                BufferedImage curve = tiles.get("curve");
                BufferedImage large_curve = tiles.get("large_curve");

                int previousEdge = edges.get(edges.size() - 1);
                for (int e = 0; e < tile.getEdges().size(); e++) {
                    int currentEdge = edges.get(e);
                    if (previousEdge != currentEdge) { // vérifier si l'arête actuelle est différente de l'arête précédente
                        int difference = edgeDifference(previousEdge, currentEdge, 6);
                        if (difference == 1) {
                            g2d.drawImage(rotateImage(curve, (previousEdge * 60)), x,
                                    y, null);
                        } else if (difference == 2) {
                            g2d.drawImage(rotateImage(large_curve, (previousEdge * 60)), x,
                                    y, null);
                        }else if (difference ==3 && edges.size()==2){
                            g2d.drawImage(rotateImage(line, (previousEdge * 60)), x,
                                    y, null);
                        }
                    }
                    previousEdge = currentEdge;
                }
            }
        }
        return temporaryTileViews;
    }

    protected int edgeDifference(int edge1, int edge2, int max) {
        int diff = edge2 - edge1;
        if (diff < 0) {
            diff = max + diff;
        }
        return diff;
    }

    protected ArrayList<TileView> drawSquareTiles(Point origin, Tile tile){
        int x = origin.x + (tile.getPositionX() * 120);
        int y = origin.y + (tile.getPositionY() * 120);
        ArrayList<TileView> temporaryTileViews = new ArrayList<>();
        String role = tile.getRole().toString().toLowerCase();
        HashMap<String, BufferedImage> tiles = squareGrayTiles;
        if(tile.getLit()){
            tiles = squareWhiteTiles;
        }
        BufferedImage image;
        BufferedImage connection = tiles.get("connection");
        BufferedImage squareImage = tiles.get("square");
        g2d.drawImage(squareImage, x, y, null);
        temporaryTileViews.add(new TileView(tile, squareImage, new Point(x, y)));
        if (!role.equalsIgnoreCase("empty")) {
            image = tiles.get(role);
            for (int e : tile.getEdges()) {
                g2d.drawImage(rotateImage(connection,  e * 90 ), x, y, null);
            }
            g2d.drawImage(image, x, y, null);

        } else {//connection tiles
            if (tile.getEdges().size() != 0) {
                ArrayList<Integer> edges = tile.getEdges();
                BufferedImage line = tiles.get("line");
                BufferedImage curve = tiles.get("curve");
                int previousEdge = edges.get(edges.size() - 1);
                for (int e = 0; e < tile.getEdges().size(); e++) {
                    int currentEdge = edges.get(e);
                    if (previousEdge != currentEdge) { // vérifier si l'arête actuelle est différente de l'arête précédente
                        int difference = edgeDifference(previousEdge, currentEdge, 4);
                        if (difference == 1) {
                            g2d.drawImage(rotateImage(curve, (previousEdge * 90)), x,
                                    y, null);
                        }else if (difference ==2  && edges.size() ==2){
                            g2d.drawImage(rotateImage(line, (previousEdge * 90)), x,
                                    y, null);
                        }
                    }
                    previousEdge = currentEdge;
                }
            }
        }
        return temporaryTileViews;
    }


    protected HashMap<String, BufferedImage> createGraySquareTiles() {
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

    protected HashMap<String, BufferedImage> createWhiteSquareTiles() {
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

    protected BufferedImage rotateImage(BufferedImage img, int degree) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degree), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(img, null);
        return rotatedImage;
    }

    protected HashMap<String, BufferedImage> createGrayHexagoneTiles() {

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

    private HashMap<String, BufferedImage> createWhiteHexagoneTiles() {

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

    public ArrayList<TileView> getTileViews() {
        return tileViews;
    }

    @Override
    public void update(Board b) {
        this.board = b;
        repaint();
    }

}
