package views;

import listeners.NavigateBackListener;
import models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardView extends JPanel {
    private JFrame frame;
    private final int level;
    private Board board;
    public BoardView(JFrame frame, int level){
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

        //createPngTiles();
    }

    private void createPngTiles(){
        BufferedImage gridImage = null;
        try {
            gridImage = ImageIO.read(new File("src/res/carte_tuiles.png"));

        // Calculate the width and height of each small image
        int smallWidth = gridImage.getWidth() / 8;
        int smallHeight = gridImage.getHeight() / 6;

        // Crop the grid image into 9 small images
        int count = 1;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 6; x++) {
                BufferedImage smallImage = new BufferedImage(smallWidth, smallHeight, gridImage.getType());
                smallImage.getGraphics().drawImage(gridImage, 0, 0, smallWidth, smallHeight, smallWidth*x, smallHeight*y, smallWidth*(x+1), smallHeight*(y+1), null);
                ImageIO.write(smallImage, "png", new File("small" + count + ".png"));
                count++;
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            System.out.println(t.getRole().toString());
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

            JPanel tilePanel = new JPanel();
            tilePanel.setLayout(new BorderLayout());
            tilePanel.add(borderLabel, BorderLayout.CENTER);

            // Add roleLabel to the center of borderLabel using GridBagLayout
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            borderLabel.setLayout(new GridBagLayout());
            borderLabel.add(roleLabel, gbc);

            panel.add(tilePanel);
        }

        return panel;

    }
    private void launch(){
        Level level = new Level(this.level);
        if(level.getShape()== 'H'){
            board = new HexaBoard(level.getTileConfig(), level.getWidth(), level.getHeight());

            add(createImages( "hexagone"));
            System.out.println("size : "+board.getRows() + " "+  board.getColumns());

            System.out.println("hexa board \n" );

        }else{
            board = new SquareBoard(level.getTileConfig(), level.getWidth(), level.getHeight());
            System.out.println("size : "+board.getRows() + " "+  board.getColumns());
            add(createImages( "square"));

            System.out.println("square board \n" +  board.getBoard());

        }
    }
}
