package views;

import models.Board;
import models.Level;
import models.Observer;
import models.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BoardAbstract extends JPanel implements Observer {
    protected Graphics2D g2d;
    protected ArrayList<TileView> tileViews;
    protected Level level;
    protected Board board;
    public JFrame frame;
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

    protected int getTileSize(){
        int tileSize = board.isSquare()? 120 : 104;
        if(board.getRows() *tileSize >= 700 || board.getColumns() *tileSize >= 800){
            tileSize = (int) (0.75 *tileSize);
        }
        return tileSize;
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
        HashMap<String, BufferedImage> tiles = TilesFactory.hexaGrayTiles;
        if(tile.getLit()){
            tiles = TilesFactory.hexaWhiteTiles;
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
        int x = origin.x + (tile.getPositionX() * getTileSize());
        int y = origin.y + (tile.getPositionY() * getTileSize());
        ArrayList<TileView> temporaryTileViews = new ArrayList<>();
        String role = tile.getRole().toString().toLowerCase();
        HashMap<String, BufferedImage> tiles = TilesFactory.squareGrayTiles;
        if(tile.getLit()){
            tiles = TilesFactory.squareWhiteTiles;
        }

        BufferedImage image;
        int tileSize = getTileSize();
        BufferedImage connection = tiles.get("connection");
        BufferedImage squareImage = tiles.get("square");
        g2d.drawImage(squareImage,  x, y, getTileSize(), getTileSize(),null);
        temporaryTileViews.add(new TileView(tile, squareImage, new Point(x, y)));
        if (!role.equalsIgnoreCase("empty")) {
            image = tiles.get(role);
            for (int e : tile.getEdges()) {
                g2d.drawImage(rotateImage(connection,  e * 90 ), x, y, getTileSize(), getTileSize(), null);
            }
            g2d.drawImage(image, x, y, getTileSize(), getTileSize(),null);

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
                            g2d.drawImage(rotateImage(curve, (previousEdge * 90)),   x,
                                    y,getTileSize(), getTileSize(), null);
                        }else if (difference ==2  && edges.size() ==2){
                            g2d.drawImage(rotateImage(line, (previousEdge * 90)),  x,
                                    y,getTileSize(), getTileSize(), null);
                        }
                    }
                    previousEdge = currentEdge;
                }
            }
        }
        return temporaryTileViews;
    }




    protected BufferedImage rotateImage(BufferedImage img, int degree) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degree), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(img, null);
        return rotatedImage;
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
