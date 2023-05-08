package controllers;
import models.Board;
import models.Level;
import models.Tile;
import views.AddTileView;
import views.EditGameView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditmodeController extends Controller {
        private final EditGameView view;
        private AddTileView addTileView;
        private JFrame frame;
        private Board board;
        private Board modification_board;


    public EditmodeController(JFrame frame,EditGameView view, Board board,NavigationController nc) {
        this.frame = frame;
        this.navigationController = nc;
        this.view = view;
        this.view.goBackBtn.addActionListener(e -> goBackConfirmListener());
        this.view.validateModif.addActionListener(e -> validateBoardListener());
        this.view.emtpyBoardItem.addActionListener(e -> emptyBoardListener());
        this.view.modifGeoBoardItem.addActionListener(e->modifGeoBoardListener());
        this.view.addTileItem.addActionListener(e->addTileListener());
        this.view.modifTileItem.addActionListener(e->modifTileListener());
        this.board = board;
        this.modification_board = this.board;
        this.addTileView = new AddTileView(frame,this.view.getLevel(),this.modification_board.getShape(), this.modification_board);
        AddTileController ac = new AddTileController(this.frame,this.modification_board,this.addTileView,this.view,this,navigationController);

    }

    public void goBackConfirmListener() {
        int confirm = JOptionPane.showConfirmDialog(
                this.frame, "Before going back, do you want to save the change(s) that you have made?\n" +
                        "Please note that the changes will be saved only if you provided a winning configuration.", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if(!this.modification_board.isBoardWinningConfig()){
                NavigationController.getInstance(frame).goBack();
            }else{
                // save the changes
                this.board = this.modification_board;
            }
        }else{
            NavigationController.getInstance(frame).goBack();
        }
    }
    public void modifTileListener() {
       /* NavigationController.getInstance(frame).navigateTo(this, new ModifyTileView(this.frame,this.level, this.modification_board));
        EditmodeController ec = new EditmodeController(frame, getView().getSelectedLevel(), null, navigationController);
        navigationController.navigateTo(view, new EditGameView(frame,  view.getSelectedLevel(),null, ec));*/
    }

    public void modifGeoBoardListener() {
        int confirm = JOptionPane.showConfirmDialog(
                this.frame, "Are you sure you want to change the board's shape?\n" +
                        "By doing that, the board will be empty", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            this.modification_board.clearBoard();
            if (this.modification_board.isSquare()){
                this.modification_board= new Board(0,0,new ArrayList<Tile>(), false);
                JDialog dialog = new JDialog(this.frame, "Board Shape Change", true);
                JLabel label = new JLabel("The board is now an Hexagon!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                dialog.add(label);
                dialog.setSize(400, 100);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }else{
                this.modification_board=new Board(0,0,new ArrayList<Tile>(),true);
                JDialog dialog = new JDialog(this.frame, "Board Shape Change", true);
                JLabel label = new JLabel("The board is now a Square!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                dialog.add(label);
                dialog.setSize(400, 100);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
            this.view.repaint();
        }
    }



    public void emptyBoardListener() {// TODO update view
        this.modification_board.clearBoard();
        JDialog dialog = new JDialog(this.frame, "Board Cleared", true);
        JLabel label = new JLabel("Board is all cleared!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void addTileListener() {
        this.addTileView = new AddTileView(frame,this.view.getLevel(),this.modification_board.getShape(), this.modification_board);
        AddTileController ac = new AddTileController(this.frame,this.modification_board,this.addTileView,this.view,this,navigationController);
        navigationController.navigateTo(view, this.addTileView);
    }

    public void validateBoardListener() { // TODO write in level file
       if (this.modification_board.isBoardWinningConfig()){
            JDialog dialog = new JDialog(this.frame, "Board Winning Config", true);
            JLabel label = new JLabel("Board is in winning configuration!");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        }
        else{
            JDialog dialog = new JDialog(this.frame, "Board Winning Config", true);
            JLabel label = new JLabel("Board is not in a winning configuration.");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        }

    }

    public void setModification_board(Board b){
        this.modification_board=b;
        this.view.repaint();
    }




}
