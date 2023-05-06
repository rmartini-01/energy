package views;

import controllers.BoardController;
import listeners.NavigateBackListener;
import models.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BoardView extends JPanel{
    private  Level level;
    private  JFrame frame;
    private ArrayList<TileView> tileViews;
    private Graphics2D g2d;
    private HashMap<String, BufferedImage> squareGrayTiles = createGraySquareTiles();
    private BoardController boardController;
    private Board board;
    public ArrayList<TileView> getTileViews() {
        return tileViews;
    }

    // Update the constructor to accept a BoardController parameter
    public BoardView(JFrame frame, int level, Board b, BoardController boardController) {
        this.frame = frame;
        this.level = new Level(level);
        tileViews = new ArrayList<>();
        this.board = b;
        setName("Board");
        add(goBackBtn(), BorderLayout.PAGE_START);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        Point origin = new Point(80, 70);
        if(tileViews.size()==0){
            if(level.getShape()=='H') {
                createHexagoneBoard(g2d,  origin);
            }else {
                createSquareBoard(g2d, origin);
            }
        }else{
            System.out.println("should repaint ");
            ArrayList<TileView> newTileView = new ArrayList<>();
            for(TileView tv : tileViews) {
                Tile tile = tv.getTile();
                newTileView.addAll(drawSquareTile(origin, tile));
            }

            tileViews = newTileView;
        }

    }

    public boolean contains(Point tilePosition , Point mousePosition){
        return tilePosition.x <= mousePosition.x && mousePosition.x <= tilePosition.x + 104
                && tilePosition.y <= mousePosition.y && mousePosition.y <= tilePosition.y + 104;
    }
    private JButton goBackBtn(){
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

    private void createHexagoneBoard(Graphics2D g2d, Point origin){
        HashMap<String, BufferedImage> grayTiles = createGrayHexagoneTiles();
        for (int i = 0; i < board.getBoard().size(); i++){
            Tile tile = board.getBoard().get(i);
            int posX = tile.getPositionX();
            int posY = tile.getPositionY();
            int x = origin.x + (posX * 104);
            int y = origin.y + (posY * 104);
            String role = tile.getRole().toString().toLowerCase();
            BufferedImage hexaImage = grayTiles.get("hexagone");
            BufferedImage connection = grayTiles.get("connection");
            BufferedImage image;
            if (!role.equalsIgnoreCase("empty")) {
                image = grayTiles.get(role);
                for (int e : tile.getEdges()) {
                    switch (e) {
                        case 0 : {
                            g2d.drawImage(connection, x, y, null);
                        }
                        case 1 : {
                            g2d.drawImage(rotateImage(connection, 55), x, y, null);
                        }
                        case 2: {
                            g2d.drawImage(rotateImage(connection, -55), x+55, y+45, null);
                        }
                        case 3 : {
                            g2d.drawImage(connection, x , 75+y, null);
                        } case 4: {
                            g2d.drawImage(rotateImage(connection, 55), x-55, y+45, null);
                        }
                        case 5 : {
                            g2d.drawImage(rotateImage(connection, -55), x, y, null);
                        }
                    }
                }
                g2d.drawImage(hexaImage, x, y, null);
                g2d.drawImage(image, x, y, null);
            } else {
                g2d.drawImage(hexaImage, x, y, null);

                if(tile.getEdges().size()!=0){
                    image = grayTiles.get("curve");
                    g2d.drawImage(image, x, y, null);
                    //todo handle empty tile here
                }
            }
        }
    }
    private void createSquareBoard(Graphics2D g2d, Point origin) {
        tileViews.clear();
        for (int i = 0; i < board.getBoard().size(); i++) {
            Tile tile = board.getBoard().get(i);
            tileViews.addAll(drawSquareTile(origin, tile));
        }
    }
    private ArrayList<TileView> drawSquareTile(Point origin, Tile tile){
        int x = origin.x + (tile.getPositionX() * 120);
        int y = origin.y + (tile.getPositionY() * 120);
        ArrayList<TileView> temporaryTileViews = new ArrayList<>();
        String role = tile.getRole().toString().toLowerCase();
        BufferedImage image;
        BufferedImage connection = squareGrayTiles.get("connection");
        BufferedImage squareImage = squareGrayTiles.get("square");
        g2d.drawImage(squareImage, x, y, null);
        temporaryTileViews.add(new TileView(tile, squareImage, new Point(x , y)));
        if (!role.equalsIgnoreCase("empty")) {
            image = squareGrayTiles.get(role);
            for (int e : tile.getEdges()) {
                switch (e) {
                    case 0 : {
                        System.out.println(tile.getRole() + " + " + e);
                        g2d.drawImage(connection, x, y, null);
                    }
                    case 1 : {
                        g2d.drawImage(rotateImage(connection, 90), x, y, null);
                    }
                    case 2 : {
                        g2d.drawImage(connection, x, y + 90, null);
                    }
                    case 3 : {
                        g2d.drawImage(rotateImage(connection, 90), x - 90, y, null);
                    }
                }
            }
            g2d.drawImage(image, x, y, null);

        } else {//connection tiles
            ArrayList<Integer> edges = tile.getEdges();
            BufferedImage line = squareGrayTiles.get("line");
            BufferedImage curve = squareGrayTiles.get("curve");
            if(edges.size() == 2 ){
                if (edges.get(0) == 0 && edges.get(1)== 2 ) {
                    g2d.drawImage(line, x, y, null);
                }
                else if((edges.get(0) == 1 && edges.get(1)== 3)){
                    g2d.drawImage(rotateImage(line, 90) , x, y, null);
                }else if(edges.get(0) == 0 && edges.get(1)== 3){
                    g2d.drawImage(rotateImage(curve, 270), x, y, null);
                }else if(edges.get(0) == 1 && edges.get(1)== 2){
                    g2d.drawImage(rotateImage(curve, 90), x, y, null);
                }else if(edges.get(0) == 2 && edges.get(1)== 3){
                    g2d.drawImage(rotateImage(curve, 180), x, y, null);
                }
                else{
                    g2d.drawImage(curve, x, y, null);
                }
            }else if (edges.size()==3){
                int edge0 = edges.get(0);
                int edge1 = edges.get(1);
                int edge2 = edges.get(2);
                if(edge0 == 0 && edge1 ==1 && edge2 ==2 ) {
                    g2d.drawImage(curve, x, y, null);
                    g2d.drawImage(rotateImage(curve, 90), x, y, null);
                }else if(edge0 == 0 && edge1==2 && edge2==3){
                    g2d.drawImage(line, x, y, null);
                    g2d.drawImage(rotateImage(curve, 180), x, y, null);
                }else {
                    g2d.drawImage(rotateImage(curve, 90), x, y, null);
                    g2d.drawImage(rotateImage(curve, 180), x, y, null);
                }

            }else if(edges.size()== 4){
                //edges size = 4
                g2d.drawImage(line, x, y, null);
                g2d.drawImage(rotateImage(line, 90), x, y, null);
            }
        }
        return temporaryTileViews;
    }

    public BufferedImage rotateImage(BufferedImage img, int degree){
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degree), img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(img, null);
        return rotatedImage;
    }

    private HashMap<String, BufferedImage> createGraySquareTiles(){
        BufferedImage gridImage = null;
        HashMap<String, BufferedImage> grayImages= new HashMap<>();
        try {
            gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage square = gridImage.getSubimage(0, 0, 120, 120);
            BufferedImage wifi = gridImage.getSubimage(120, 120, 120, 120);
            BufferedImage lamp = gridImage.getSubimage(240, 120, 120, 120);
            BufferedImage connection = gridImage.getSubimage(0 ,240, 120 , 120);
            BufferedImage curve = gridImage.getSubimage(120 ,240, 120 , 120);
            BufferedImage line = gridImage.getSubimage(240 ,240, 120 , 120);
            BufferedImage source = gridImage.getSubimage(0, 480, 120, 120);

            grayImages.put("square", square);
            grayImages.put("wifi", wifi);
            grayImages.put("lamp", lamp);
            grayImages.put("connection", connection);
            grayImages.put("curve", curve);
            grayImages.put("line", line) ;
            grayImages.put("source", source) ;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grayImages;
    }

    private HashMap<String, BufferedImage> createGrayHexagoneTiles(){

        HashMap<String, BufferedImage> grayImages= new HashMap<>();
        try {
            BufferedImage gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage hexagone = gridImage.getSubimage(360, 0, 120, 104);
            BufferedImage wifi = gridImage.getSubimage(480, 120, 120, 104);
            BufferedImage lamp = gridImage.getSubimage(600, 120, 120, 104);

            BufferedImage connection = gridImage.getSubimage(360 ,240, 120 , 104);
            BufferedImage curve = gridImage.getSubimage(480 ,240, 120 , 104);
            BufferedImage large_curve = gridImage.getSubimage(600 ,240, 120 , 104);
            BufferedImage line = gridImage.getSubimage(720 ,240, 120 , 104);
            BufferedImage source = gridImage.getSubimage(360, 480, 120, 104);

            grayImages.put("hexagone", hexagone);
            grayImages.put("wifi", wifi);
            grayImages.put("lamp", lamp);
            grayImages.put("connection", connection);
            grayImages.put("curve", curve);
            grayImages.put("large_curve", large_curve);
            grayImages.put("line", line) ;
            grayImages.put("source", source) ;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return grayImages;
    }
}
