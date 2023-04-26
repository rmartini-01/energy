package views;

import controllers.NavigationController;
import listeners.NavigateBackListener;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditGameView extends JPanel {
    private JFrame frame;
    private final int level;
    private Board board;
    private Board modification_board;
    public EditGameView(JFrame frame,int level,Board modif_board) {
        this.level = level;
        Level l = new Level (level);
        if (l.getShape()=='S'){
            this.board=new Board (l.getWidth(), l.getHeight(),l.getTileConfig(),true);
            this.board.setNeighborsSquare();
            this.board.initNeighborsList();
        }else{
            this.board=new Board (l.getWidth(), l.getHeight(),l.getTileConfig(), false);
            this.board.setNeighborsHexa();
            this.board.initNeighborsList();
        }
        this.frame = frame;
        if (modif_board==null){
            this.modification_board = this.board;
        }else{
            this.modification_board = modif_board;
        }
        for (Tile t : this.board.getBoard()){
            t.printTile();
        }




        setName("Edit Mode Level " + level);
        ImageIcon icon = new ImageIcon("src/res/back-icon-white.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton goBackBtn = new JButton();
        goBackBtn.setIcon(scaledIcon);
        goBackBtn.setPreferredSize(new Dimension(25, 25));
        goBackBtn.addActionListener(this::goBackConfirmListener);
        goBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(goBackBtn);
        JMenuBar EditMenuBar = new JMenuBar();
        EditMenuBar.setBounds(0, 0, 200, 200);
        JMenu EditBoardMenu = new JMenu("Edit Board");
        JMenuItem emtpyBoardItem = new JMenuItem("Empty Board");
        JMenuItem modifGeoBoardItem = new JMenuItem("Modify Board Shape");
        EditBoardMenu.add(emtpyBoardItem);
        EditBoardMenu.add(modifGeoBoardItem);
        JMenu EditTileMenu = new JMenu("Edit Tile");
        JMenuItem addTileItem = new JMenuItem("Add a Tile");
        JMenuItem modifTileItem = new JMenuItem("Modify a Tile");
        EditTileMenu.add(addTileItem);
        EditTileMenu.add(modifTileItem);
        EditMenuBar.add(EditBoardMenu);
        EditMenuBar.add(EditTileMenu);
        add(EditMenuBar);
        JButton validateModif = new JButton ("Validate");
        validateModif.addActionListener(this::validateBoardListener);
        add(validateModif);
        emtpyBoardItem.addActionListener(this::emptyBoardListener);
        modifGeoBoardItem.addActionListener(this::modifGeoBoardListener);
        addTileItem.addActionListener(this::addTileListener);
        modifTileItem.addActionListener(this::modifTileListener);
        ArrayList<Tile> tl = new ArrayList<>();
        tl.add(new Tile(0, 0, 0, 'S', Role.EMPTY, new ArrayList<Integer>()));
        this.board = new Board(2, 2, tl,true);
    }

    private void goBackConfirmListener(ActionEvent actionEvent) {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Before going back, do you want to save the change(s) that you have made?\n" +
                        "Please note that the changes will be saved only if you provided a winning configuration.", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if(!this.modification_board.isBoardWinningConfig()){
                NavigationController.getInstance(frame).goBack();
            }else{
                // save the changes
                this.board=this.modification_board;
            }
        }else{
            NavigationController.getInstance(frame).goBack();
        }
    }

    private void modifTileListener(ActionEvent actionEvent) {
        NavigationController.getInstance(frame).navigateTo(this, new ModifyTileView(this.frame,this.level, this.modification_board));
    }

    private void modifGeoBoardListener(ActionEvent actionEvent) {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to change the board's shape?\n" +
                        "By doing that, the board will be empty", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
                this.modification_board.clearBoard();
                if (this.modification_board.isSquare()){
                    this.modification_board = new Board(this.board.getColumns(),this.board.getRows(),new ArrayList<Tile>(), false);
                    JDialog dialog = new JDialog(this.frame, "Board Shape Change", true);
                    JLabel label = new JLabel("The board is now an Hexagon!");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    dialog.add(label);
                    dialog.setSize(400, 100);
                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);
                }else{
                    this.modification_board = new Board(this.board.getColumns(),this.board.getRows(),new ArrayList<Tile>(),true);
                    JDialog dialog = new JDialog(this.frame, "Board Shape Change", true);
                    JLabel label = new JLabel("The board is now a Square!");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    dialog.add(label);
                    dialog.setSize(400, 100);
                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);
                }
        }
    }

    private void emptyBoardListener(ActionEvent actionEvent) {
        this.modification_board.clearBoard();
        JDialog dialog = new JDialog(this.frame, "Board Cleared", true);
        JLabel label = new JLabel("Board is all cleared!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void addTileListener(ActionEvent event) {
        NavigationController.getInstance(frame).navigateTo(this, new AddTileView(this.frame,this.level, this.modification_board ));
    }

    private void validateBoardListener(ActionEvent event) {

        if (this.modification_board.isBoardWinningConfig()){
            JDialog dialog = new JDialog(this.frame, "Board Winning Config", true);
            JLabel label = new JLabel("Board is winning");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        }
        else{
            JDialog dialog = new JDialog(this.frame, "Board Winning Config", true);
            JLabel label = new JLabel("Board is not winning");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        }
    }




}
