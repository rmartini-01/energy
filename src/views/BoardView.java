package views;

import listeners.NavigateBackListener;
import models.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class BoardView extends JPanel implements Observer {
    private Level level;
    private JFrame frame;
    private ArrayList<TileView> tileViews;
    private Graphics2D g2d;
    private HashMap<String, BufferedImage> squareGrayTiles = createGraySquareTiles();
    private HashMap<String, BufferedImage> hexaGrayTiles = createGrayHexagoneTiles();

    private Board board;

    public BoardView(JFrame frame, Level level, Board board) {
        this.frame = frame;
        this.level = level;
        this.board = board;
        tileViews = new ArrayList<>();
        setName("Board");
        add(goBackBtn());

    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        Point origin = new Point(80, 70);
        if (tileViews.size() == 0) {
            if (level.getShape() == 'H') {
                createHexagoneBoard(g2d, origin);
            } else {
                createSquareBoard(g2d, origin);
            }
        } else {
            ArrayList<TileView> newTileView = new ArrayList<>();
            if (level.getShape() == 'H') {
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

    }

    public boolean contains(Point tilePosition, Point mousePosition, int width, int height) {
        return tilePosition.x <= mousePosition.x && mousePosition.x <= tilePosition.x + width
                && tilePosition.y <= mousePosition.y && mousePosition.y <= tilePosition.y + height;
    }

    private JButton goBackBtn() {
        JButton goBackBtn = new JButton();
        ImageIcon icon = new ImageIcon("src/res/back-icon-white.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        goBackBtn.setIcon(scaledIcon);
        goBackBtn.setPreferredSize(new Dimension(25, 25));
        goBackBtn.addActionListener(new NavigateBackListener(frame));
        goBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return goBackBtn;
    }

    private void createHexagoneBoard(Graphics2D g2d, Point origin) {
        tileViews.clear();
        for (int i = 0; i < board.getBoard().size(); i++) {
            Tile tile = board.getBoard().get(i);
            tileViews.addAll(drawHexaTiles(origin, tile));
        }
    }

    private ArrayList<TileView> drawHexaTiles(Point origin, Tile tile) {
        int posX = tile.getPositionX();
        int posY = tile.getPositionY();
        int x = origin.x + (posX * 104);
        int y = origin.y + (posY * 104);
        String role = tile.getRole().toString().toLowerCase();
        BufferedImage hexaImage = hexaGrayTiles.get("hexagone");
        BufferedImage connection = hexaGrayTiles.get("connection");
        BufferedImage image;
        ArrayList<TileView> temporaryTileViews = new ArrayList<>();
        temporaryTileViews.add(new TileView(tile, hexaImage, new Point(x, y)));
        g2d.drawImage(hexaImage, x, y, null);
        if (!role.equalsIgnoreCase("empty")) {
            image = hexaGrayTiles.get(role);
            for (int e = 0; e < tile.getEdges().size(); e++) {
                g2d.drawImage(rotateImage(connection,
                                    (tile.getEdges().get(e) * 60) ),
                            x, y, null);
            }
            g2d.drawImage(image, x, y, null);
        } else {

            if (tile.getEdges().size() != 0) {
                ArrayList<Integer> edges = tile.getEdges();
                BufferedImage line = hexaGrayTiles.get("line");
                BufferedImage curve = hexaGrayTiles.get("curve");
                BufferedImage large_curve = hexaGrayTiles.get("large_curve");

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

    private int edgeDifference(int edge1, int edge2, int max) {
        int diff = edge2 - edge1;
        if (diff < 0) {
            diff = max + diff;
        }
        return diff;
    }

    private void createSquareBoard(Graphics2D g2d, Point origin) {
        tileViews.clear();
        for (int i = 0; i < board.getBoard().size(); i++) {
            Tile tile = board.getBoard().get(i);
            tileViews.addAll(drawSquareTiles(origin, tile));
        }
    }


    private ArrayList<TileView> drawSquareTiles(Point origin, Tile tile){
        int x = origin.x + (tile.getPositionX() * 120);
        int y = origin.y + (tile.getPositionY() * 120);
        ArrayList<TileView> temporaryTileViews = new ArrayList<>();
        String role = tile.getRole().toString().toLowerCase();
        BufferedImage image;
        BufferedImage connection = squareGrayTiles.get("connection");
        BufferedImage squareImage = squareGrayTiles.get("square");
        g2d.drawImage(squareImage, x, y, null);
        temporaryTileViews.add(new TileView(tile, squareImage, new Point(x, y)));
        if (!role.equalsIgnoreCase("empty")) {
            image = squareGrayTiles.get(role);
            for (int e : tile.getEdges()) {
                g2d.drawImage(rotateImage(connection,  e * 90 ), x, y, null);
            }
            g2d.drawImage(image, x, y, null);

        } else {//connection tiles
            if (tile.getEdges().size() != 0) {
            ArrayList<Integer> edges = tile.getEdges();
            BufferedImage line = squareGrayTiles.get("line");
            BufferedImage curve = squareGrayTiles.get("curve");
            int previousEdge = edges.get(edges.size() - 1);
                System.out.println(edges);
            for (int e = 0; e < tile.getEdges().size(); e++) {
                    int currentEdge = edges.get(e);
                    if (previousEdge != currentEdge) { // vérifier si l'arête actuelle est différente de l'arête précédente
                        int difference = edgeDifference(previousEdge, currentEdge, 4);
                        if (difference == 1) {
                            g2d.drawImage(rotateImage(curve, (previousEdge * 90)), x,
                                    y, null);
                        }else if (difference ==2 ){
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
    public BufferedImage rotateImage(BufferedImage img, int degree) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degree), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(img, null);
        return rotatedImage;
    }

    private HashMap<String, BufferedImage> createGraySquareTiles() {
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

    private HashMap<String, BufferedImage> createGrayHexagoneTiles() {

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
