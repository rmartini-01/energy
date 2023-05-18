package views;

import models.Board;
import models.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EditGameView extends BoardAbstract  {
    public JFrame frame;
    public HomepageView frameHome;
    public JMenuBar editMenuBar = new JMenuBar();
    public JMenu editBoardMenu = new JMenu("Edit Board");
    public JMenu editTileMenu = new JMenu("Edit Tile");
    public JMenuItem addTileItem = new JMenuItem("Add a Tile");
    public JMenuItem modifTileItem = new JMenuItem("Modify a Tile");
    public JMenuItem removeTileItem = new JMenuItem("Remove a Tile");
    public JMenuItem emtpyBoardItem = new JMenuItem("Empty Board");
    public JMenuItem modifGeoBoardItem = new JMenuItem("Modify Board Shape");
    public JButton validateModif = new JButton ("Validate");
    public JButton goBackBtn = new JButton();

    public EditGameView(JFrame frame, Level level, Board board, HomepageView home) {
        this.frame=frame;
        this.frameHome=home;
        this.level=level;
        this.tileViews = new ArrayList<>();
        this.board = board;
        setName("Edit Mode Level " + level.getNum());
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
        this.editTileMenu.add(this.removeTileItem);
        this.editMenuBar.add(this.editBoardMenu);
        this.editMenuBar.add(this.editTileMenu);
        add(this.editMenuBar);
        add(validateModif);
    }

    public Level getLevel(){return this.level;}

    public JPanel getPanel() {
        return this;
    }

}

