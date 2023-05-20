package controllers;
import models.Board;
import models.Level;
import models.Tile;
import views.AddTileView;
import views.EditGameView;
import views.ModifyTileView;
import views.TileView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EditmodeController extends Controller {
    private final EditGameView view;
    private AddTileView addTileView;
    private ModifyTileView modifyTileView;
    private Board board;
    private boolean modification_mode = false;
    private boolean remove_mode = false;



    public EditmodeController(EditGameView view, Board board,NavigationController nc) {
        this.navigationController = nc;
        this.view = view;
        this.view.goBackBtn.addActionListener(e -> goBackConfirmListener());
        this.view.validateModif.addActionListener(e -> validateBoardListener());
        this.view.emtpyBoardItem.addActionListener(e -> emptyBoardListener());
        this.view.modifGeoBoardItem.addActionListener(e->modifGeoBoardListener());
        this.view.addTileItem.addActionListener(e->addTileListener());
        this.view.modifTileItem.addActionListener(e->modifTileListener());
        this.view.removeTileItem.addActionListener(e->removeTileListener());
        this.board = board;
        this.board.addObserver(this.view);
        this.view.getPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleTileClickEdit(e);
            }
        });
        this.view.btnDialog.addActionListener( e-> view.dialog.dispose());
        this.addTileView = new AddTileView(this.view.frame,this.view.getLevel(),this.board.getShape(), this.board);
        AddTileController ac = new AddTileController(this.view.frame,this.board,this.addTileView,this.view,this,navigationController);

    }

    public void goBackConfirmListener() {
        int confirm = JOptionPane.showConfirmDialog(
                this.view.frame, "Before going back, do you want to save the change(s) that you have made?\n" +
                        "Please note that the changes will be saved only if you provided a winning configuration.", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if(!this.board.isBoardWinningConfig()){
                NavigationController.getInstance(this.view.frame).navigateTo(this.view,this.view.frameHome);
            }else{
                this.view.getLevel().saveChanges(this.board.getLevelTxtFromBoard());
                NavigationController.getInstance(this.view.frame).navigateTo(this.view,this.view.frameHome);
            }
        }
        else{
            NavigationController.getInstance(this.view.frame).navigateTo(this.view,this.view.frameHome);
        }
    }
    public void modifTileListener() {
        JDialog dialog = new JDialog(this.view.frame, "Modify a Tile", true);
        JLabel label = new JLabel("Click on the tile you want to modify.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(this.view.frame);
        dialog.setVisible(true);
        this.modification_mode=true;
    }

    public void removeTileListener() {
        JDialog dialog = new JDialog(this.view.frame, "Remove a Tile", true);
        JLabel label = new JLabel("Click on the tile you want to remove.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(this.view.frame);
        dialog.setVisible(true);
        this.remove_mode=true;

    }

    public void modifGeoBoardListener() {
        int confirm = JOptionPane.showConfirmDialog(
                this.view.frame, "Are you sure you want to change the board's shape?\n" +
                        "By doing that, the board will be empty", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            this.board.modifGeoBoard();
            JDialog dialog = new JDialog(this.view.frame, "Board Shape Change", true);
            JLabel label;
            if (this.board.isSquare()){
                label = new JLabel("The board is now an Square!");
            }else{
                label = new JLabel("The board is now a Hexagon!");
            }
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(this.view.frame);
            dialog.setVisible(true);
        }
    }



    public void emptyBoardListener() {
        JDialog dialog = new JDialog(this.view.frame, "Board Cleared", true);
        JLabel label = new JLabel("Board is all cleared!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(this.view.frame);
        dialog.setVisible(true);
        this.board.clearBoard();
    }

    public void addTileListener() {
        this.addTileView = new AddTileView(this.view.frame,this.view.getLevel(),this.board.getShape(), this.board);
        AddTileController ac = new AddTileController(this.view.frame,this.board,this.addTileView,this.view,this,navigationController);
        navigationController.navigateTo(view, this.addTileView);
    }

    public void validateBoardListener() { // TODO write in level file
        if (this.board.isBoardWinningConfig()){
            JDialog dialog = new JDialog(this.view.frame, "Level Saved", true);
            JLabel label = new JLabel("You have modified a level!");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(this.view.frame);
            dialog.setVisible(true);
            this.view.getLevel().saveChanges(this.board.getLevelTxtFromBoard());
        }
        else{
            JDialog dialog = new JDialog(this.view.frame, "Level unsaved", true);
            JLabel label = new JLabel("Board is not in a winning configuration. The changes couldn't be saved.");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(450, 150);
            dialog.setLocationRelativeTo(this.view.frame);
            dialog.setVisible(true);
        }

    }

    public void setModification_board(Board b){
        this.board=b;
        this.view.repaint();

    }

    public void handleTileClickEdit(MouseEvent e) {
        if (modification_mode){
            Tile t = this.board.getBoard().get(0);
            Point clickPosition = e.getPoint();
            for (TileView tileView : view.getTileViews()) {
                Point tilePosition = tileView.getPosition();
                if (view.contains(tilePosition, clickPosition , tileView.getImage().getWidth(), tileView.getImage().getHeight())) {
                    t = tileView.getTile();
                }
            }
            this.modification_mode=false;
            this.modifyTileView = new ModifyTileView(this.view.frame,this.view.getLevel(),this.board.getShape(), this.board,0);
            this.modifyTileView.setId_tile(t.getId());
            ModifyTileController mc = new ModifyTileController(this.view.frame,this.board,this.modifyTileView,this.view,this,navigationController);
            navigationController.navigateTo(view, this.modifyTileView);
        }
        else if (this.remove_mode){
            this.remove_mode=false;
            Tile t = this.board.getBoard().get(0);
            Point clickPosition = e.getPoint();
            for (TileView tileView : view.getTileViews()) {
                Point tilePosition = tileView.getPosition();
                if (view.contains(tilePosition, clickPosition , tileView.getImage().getWidth(), tileView.getImage().getHeight())) {
                    t = tileView.getTile();
                }
            }
            this.board.removeTile(t.getId());

        }
        else{
            Point clickPosition = e.getPoint();
            for (TileView tileView : view.getTileViews()) {
                Point tilePosition = tileView.getPosition();
                if (view.contains(tilePosition, clickPosition , tileView.getImage().getWidth(), tileView.getImage().getHeight())) {
                    Tile t = tileView.getTile();
                    board.rotateTile(t);
                    board.lightsUp();
                }
            }
            if(board.isBoardWinningConfig()){
                view.showWinningDialog(view.getPanel());
            }
        }
    }






}
