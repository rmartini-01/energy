package views;

import listeners.NavigateBackListener;
import models.Board;
import models.HexaBoard;
import models.Level;
import models.SquareBoard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardView extends JPanel {
    private JFrame frame;
    private int level;
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
            board = new HexaBoard(level.getConfig());

        }else{
            board = new SquareBoard(level.getConfig());
        }
    }
}
