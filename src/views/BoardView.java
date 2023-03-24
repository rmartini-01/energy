package views;

import listeners.NavigateBackListener;
import models.*;

import javax.swing.*;
import java.awt.*;
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

    }

    private void launch(){
        Level level = new Level(this.level);
        if(level.getShape()== 'H'){
            board = new HexaBoard(level.getTileConfig(), level.getWidth(), level.getHeight());
            JPanel panel = new JPanel();
            panel.setBackground(Color.darkGray);
            panel.setSize(300,300);
            System.out.println("size : "+board.getRows() + " "+  board.getColumns());

            GridLayout layout = new GridLayout(0,board.getColumns());
            layout.setHgap(10);
            layout.setVgap(10);

            panel.setLayout(layout);
            for(Tile t : board.getBoard()) {

                panel.add(new JLabel(t.getRole().toString()));
            }
            add(panel);
            System.out.println("hexa board \n" );
            // Create a new GridPane
          /*  this.setLayout(new GridLayout(board.getRows(), board.getColumns()));

            // Loop through the board and add each tile to the grid
            for(Tile t : board.getBoard()) {
                t.printTile();
                // Get the position of the tile
                int posX = t.getPositionX();
                int posY = t.getPositionY();
                JLabel label = new JLabel(t.getRole().toString());
                // Add the tile to the grid at the appropriate position
                add(label, posX, posY);
            }*/
        }else{
            board = new SquareBoard(level.getTileConfig(), level.getWidth(), level.getHeight());
            System.out.println("size : "+board.getRows() + " "+  board.getColumns());

            JPanel panel = new JPanel();
            panel.setBackground(Color.darkGray);
            panel.setSize(300,300);
            GridLayout layout = new GridLayout(0,board.getColumns());
            layout.setHgap(10);
            layout.setVgap(10);

            panel.setLayout(layout);
            for(Tile t : board.getBoard()) {

                panel.add(new JLabel(t.getRole().toString()));
            }
            add(panel);
            System.out.println("hexa board \n" );
            System.out.println("square board \n" +  board.getBoard());
            // Loop through the board and add each tile to the grid
            /*for(Tile t : board.getBoard()) {
                t.printTile();
                // Get the position of the tile
                int posX = t.getPositionX();
                int posY = t.getPositionY();
                JLabel label = new JLabel(t.getRole().toString());
                // Add the tile to the grid at the appropriate position
                add(label, posX, posY);
            }*/
        }
    }
}
