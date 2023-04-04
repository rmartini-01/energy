package views;

import listeners.NavigateBackListener;
import models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BoardView extends JPanel {
    private JFrame frame;
    private final int level;
    private Board board;
    public BoardView(JFrame frame, int level) {
        this.frame = frame;
        this.level = level;
        setName("Board");
        JButton goBackBtn = new JButton();
        ImageIcon icon = new ImageIcon("src/res/back-icon-white.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        goBackBtn.setIcon(scaledIcon);
        goBackBtn.setPreferredSize(new Dimension(25, 25));
        goBackBtn.addActionListener(new NavigateBackListener(frame));
        goBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(goBackBtn, BorderLayout.PAGE_START);
        launch();
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
        BufferedImage gridImage ;
        HashMap<String, BufferedImage> grayImages= new HashMap<>();
        try {
            gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage hexagone = gridImage.getSubimage(360, 0, 120, 104);
            BufferedImage wifi = gridImage.getSubimage(480, 120, 120, 104);
            BufferedImage lamp = gridImage.getSubimage(600, 120, 120, 104);

            BufferedImage connection = gridImage.getSubimage(360 ,240, 120 , 104);
            BufferedImage curve = gridImage.getSubimage(480 ,240, 120 , 104);
            BufferedImage large_curve = gridImage.getSubimage(600 ,240, 120 , 104);
            BufferedImage line = gridImage.getSubimage(720 ,240, 120 , 104);
            BufferedImage source = gridImage.getSubimage(480, 480, 120, 104);

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

    private HashMap<String, BufferedImage> createWhiteSquareTiles(){
        BufferedImage gridImage = null;
        HashMap<String, BufferedImage> grayImages= new HashMap<>();
        try {
            gridImage = ImageIO.read(new File("src/res/tuiles.png"));
            BufferedImage square = gridImage.getSubimage(0, 360, 120, 120);
            BufferedImage wifi = gridImage.getSubimage(480, 120, 120, 120);
            BufferedImage lamp = gridImage.getSubimage(480, 240, 120, 120);
            BufferedImage source = gridImage.getSubimage(0, 480, 120, 120);
            BufferedImage connection = gridImage.getSubimage(0 ,600, 120 , 120);
            BufferedImage curve = gridImage.getSubimage(120 ,600, 120 , 120);
            BufferedImage line = gridImage.getSubimage(240 ,600, 120 , 120);

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
    private String createFileName(String shape, String role){
        return "src/res/"+shape+"_images/"+role+".png";
    }
    private JPanel createImages( String shape){
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(300,300);

        GridLayout layout;

        if(shape.equals("hexagone")){
            layout= new GridLayout(board.getRows(),0);
        }else{
            layout = new GridLayout(0, board.getColumns());
        }

        panel.setLayout(layout);
        for(Tile t : board.getBoard()) {

            String roleFilename = "";
            String role = t.getRole().toString().toLowerCase();
            if(!role.equalsIgnoreCase("empty")){
              roleFilename  ="src/res/"+shape+"_images/"+role+".png";

            }
            else{
                //todo corrgier ici
                roleFilename = "src/res/"+shape+"_images/line.png";
            }
            String borderFilename = "src/res/"+shape+"_images/"+shape+".png";
            System.out.println(t.getRole().toString() + " edges = " + t.getEdges().toString());
            ImageIcon roleIcon = new ImageIcon(roleFilename);
            ImageIcon borderIcon = new ImageIcon(borderFilename);

            Image scaledRoleImage = roleIcon.getImage().getScaledInstance(
                    roleIcon.getIconWidth() / 2, roleIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
            roleIcon = new ImageIcon(scaledRoleImage);

            Image scaledBorderImage = borderIcon.getImage().getScaledInstance(
                    borderIcon.getIconWidth() / 2, borderIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
            borderIcon = new ImageIcon(scaledBorderImage);

            JLabel roleLabel = new JLabel(roleIcon);
            JLabel borderLabel = new JLabel(borderIcon);

            // Add roleLabel to the center of borderLabel using GridBagLayout
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            borderLabel.setLayout(new GridBagLayout());
            gbc.anchor = GridBagConstraints.CENTER;
            borderLabel.add(roleLabel, gbc);

            JPanel tilePanel = new JPanel();
            tilePanel.setLayout(new BorderLayout());
            tilePanel.add(borderLabel, BorderLayout.CENTER);

            if(!t.getRole().equals(Role.EMPTY)){

                for(int edge : t.getEdges()){
                    String connectionFilename="" ;
                    if(edge==0 || edge==2){
                         connectionFilename = createFileName(shape, "short_line");

                    }else{
                        connectionFilename = createFileName(shape, "short_line_sides");

                    }
                    ImageIcon edgeIcon= new ImageIcon(connectionFilename);
                    Image scaledConnectionImage = edgeIcon.getImage().getScaledInstance(
                            edgeIcon.getIconWidth() / 2, edgeIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
                    edgeIcon = new ImageIcon(scaledConnectionImage);

                    switch (edge) {
                        case 0 -> { // top edge

                            if(shape.equalsIgnoreCase("square")) {

                                JLabel edgeLabel = new JLabel(edgeIcon);

                                JPanel topPanel = new JPanel(new BorderLayout());
                                topPanel.add(edgeLabel, BorderLayout.NORTH);
                                gbc.anchor = GridBagConstraints.PAGE_START;
                                borderLabel.add(topPanel, gbc);
                            }else{
                                /*double angle = Math.toRadians(35);
                                AffineTransform transform = new AffineTransform();
                                transform.rotate(angle, edgeIcon.getIconWidth() / 2, edgeIcon.getIconHeight() / 2);
                                Image image = ((ImageIcon) edgeIcon).getImage();
                                Image rotatedImage = new BufferedImage(edgeIcon.getIconWidth(), edgeIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
                                g.setTransform(transform);
                                g.drawImage(image, 0, 0, null);
                                ImageIcon rotatedIcon = new ImageIcon(rotatedImage);*/
                                JLabel edgeLabel = new JLabel(edgeIcon);

                                JPanel leftPanel = new JPanel(new BorderLayout());
                                leftPanel.add(edgeLabel, BorderLayout.WEST);
                                System.out.println("tile " + edge);
                                gbc.anchor = GridBagConstraints.PAGE_END;

                                borderLabel.add(leftPanel,gbc);
                            }

                        }
                        case 1 -> { // right edge
                            JLabel edgeLabel = new JLabel(edgeIcon);

                            JPanel rightPanel = new JPanel(new BorderLayout());
                            rightPanel.add(edgeLabel, BorderLayout.EAST);
                            gbc.anchor = GridBagConstraints.LINE_END;

                            borderLabel.add(rightPanel,gbc);
                        }
                        case 2 -> { // bottom edge
                            JLabel edgeLabel = new JLabel(edgeIcon);

                            JPanel bottomPanel = new JPanel(new BorderLayout());
                            bottomPanel.add(edgeLabel, BorderLayout.SOUTH);
                            gbc.anchor = GridBagConstraints.PAGE_END;

                            borderLabel.add(bottomPanel, gbc);
                        }
                        case 3 -> { // left edge
                            JLabel edgeLabel = new JLabel(edgeIcon);

                            JPanel leftPanel = new JPanel(new BorderLayout());
                            leftPanel.add(edgeLabel, BorderLayout.WEST);
                            System.out.println("tile " + edge);
                            gbc.anchor = GridBagConstraints.LINE_START;
                            borderLabel.add(leftPanel,gbc);
                        }
                        case 4 -> {
                            System.out.println("case 4 ");
                            // assuming imageIcon contains the image you want to rotate
                        }
                        case 5->{
                            System.out.println("case 5 ");
                        }
                    }
                }
            }
            panel.add(tilePanel);
        }

        return panel;

    }


    private void launch(){
        Level level = new Level(this.level);
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(300,300);
        GridLayout layout;
        String shape = "";
        HashMap<String, BufferedImage> grayTiles;
        if(level.getShape()== 'H'){
            shape = "hexagone";
            board = new HexaBoard( level.getWidth() , level.getHeight(), level.getTileConfig());
            layout= new GridLayout(board.getRows(), board.getColumns());
            panel.setLayout(layout);
            System.out.println("size : "+board.getRows() + " "+  board.getColumns());

            System.out.println("hexa board \n" );
            grayTiles = createGrayHexagoneTiles() ;

        }else{
            shape = "square";
            board = new SquareBoard( level.getWidth() , level.getHeight(), level.getTileConfig());
            layout = new GridLayout(board.getRows(), board.getColumns());
            grayTiles = createGraySquareTiles();
        }
        panel.setLayout(layout);
        for(Tile tile : board.getBoard()){
            createSqaureTile(panel, grayTiles, tile, shape);
        }
        add(panel);
        System.out.println("square board \n" +  board.getBoard());

    }

    private void createSqaureTile(JPanel panel, HashMap<String, BufferedImage> grayTiles, Tile tile, String shape){
        JPanel tilePanel = new JPanel();
        tilePanel.setLayout(new BorderLayout());
        for(String label : grayTiles.keySet()){
            ImageIcon borderIcon = new ImageIcon(grayTiles.get(shape));
            // Create a JLabel to display the image
            JLabel border = new JLabel(borderIcon);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            border.setLayout(new GridBagLayout());
            gbc.anchor = GridBagConstraints.CENTER;
            System.out.println(tile.getRole().toString());
            if(tile.getRole().toString().equalsIgnoreCase("Empty")){
                // Load the image
                ImageIcon roleIcon = new ImageIcon(grayTiles.get("curve"));
                // Create a JLabel to display the image
                JLabel role = new JLabel(roleIcon);
                border.add(role, gbc);
                tilePanel.add(border, BorderLayout.CENTER);
            }
            if(tile.getRole().toString().equalsIgnoreCase(label)){
                ImageIcon roleIcon = new ImageIcon(grayTiles.get(label));
                JLabel role = new JLabel(roleIcon);
                border.add(role, gbc);
                for(int edge : tile.getEdges()){
                    System.out.println(edge);
                    switch (edge) {
                        case 0 -> gbc.anchor = GridBagConstraints.PAGE_START;
                        case 1 -> gbc.anchor = GridBagConstraints.LINE_END;
                        case 2 -> gbc.anchor = GridBagConstraints.PAGE_END;
                        case 3 -> gbc.anchor = GridBagConstraints.LINE_START;
                        case 4 -> System.out.println("case4");
                        case 5 -> System.out.println("case5");
                    }
                    border.add(addConnection(grayTiles.get("connection")), gbc);
                }
                tilePanel.add(border, BorderLayout.CENTER);
            }
        }
        panel.add(tilePanel);
    }

    private JLabel addConnection(BufferedImage connection){

        ImageIcon edgeIcon= new ImageIcon(connection);
        /* Image scaledConnectionImage = edgeIcon.getImage().getScaledInstance(
                            edgeIcon.getIconWidth() / 2, edgeIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
        edgeIcon = new ImageIcon(scaledConnectionImage);*/
        return new JLabel(edgeIcon);
    }
}
