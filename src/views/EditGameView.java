package views;

import controllers.EditmodeController;
import controllers.HomepageController;
import controllers.NavigationController;
import listeners.NavigateBackListener;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditGameView extends JPanel {
    public JFrame frame;
    int level;
    public JMenuBar editMenuBar = new JMenuBar();
    public JMenu editBoardMenu = new JMenu("Edit Board");
    public JMenu editTileMenu = new JMenu("Edit Tile");
    public JMenuItem addTileItem = new JMenuItem("Add a Tile");
    public JMenuItem modifTileItem = new JMenuItem("Modify a Tile");
    public JMenuItem emtpyBoardItem = new JMenuItem("Empty Board");
    public JMenuItem modifGeoBoardItem = new JMenuItem("Modify Board Shape");
    public JButton validateModif = new JButton ("Validate");
    public JButton goBackBtn = new JButton();
    public Board board;
    public Board modification_board;

    public EditGameView(JFrame frame, int level, Board board) {
        this.level=level;
        Level l = new Level(level);
        this.board = board;
        this.board.initNeighborsList();
        this.modification_board = this.board;
        this.frame=frame;
        setName("Edit Mode Level " + level);
        ImageIcon icon = new ImageIcon("src/res/back-icon-white.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.goBackBtn.setIcon(scaledIcon);
        this.goBackBtn.setPreferredSize(new Dimension(25, 25));
        this.goBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(goBackBtn);
        this.editMenuBar.setBounds(0, 0, 200, 200);
        this.editBoardMenu.add(this.emtpyBoardItem);
        this.editBoardMenu.add(this.modifGeoBoardItem);
        this.editTileMenu.add(this.addTileItem);
        this.editTileMenu.add(this.modifTileItem);
        this.editMenuBar.add(this.editBoardMenu);
        this.editMenuBar.add(this.editTileMenu);
        add(this.editMenuBar);
        add(validateModif);
    }

    public int getLevel(){return this.level;}

}
