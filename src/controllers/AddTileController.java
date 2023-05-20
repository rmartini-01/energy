package controllers;

import models.Board;
import models.Role;
import models.Tile;
import views.AddTileView;
import views.EditGameView;

import javax.swing.*;
import listeners.*;

import java.awt.*;
import java.util.ArrayList;

public class AddTileController extends Controller {
    private EditmodeController editController;
    private EditGameView editView;
    private JFrame frame;
    private final AddTileView view;
    private Board modification_board;
    private char shape;

    public AddTileController(JFrame frame ,Board modif_board,AddTileView view, EditGameView editView,EditmodeController editController,NavigationController nc ) {
        this.editView=editView;
        this.editController = editController;
        this.frame=frame;
        this.view = view;
        this.modification_board = modif_board;
        this.navigationController=nc;
        this.shape = (this.modification_board.isSquare()) ? 'S' : 'H';
        CheckActionListener listenerCheckBoxEdge;
        if (this.shape=='S'){
            listenerCheckBoxEdge = new CheckActionListener(
                    this.view.checkboxZero, this.view.checkboxOne, this.view.checkboxTwo, this.view.checkboxThree, this.view.checkboxNo);
            this.view.checkboxZero.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxOne.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxTwo.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxThree.addActionListener(listenerCheckBoxEdge);
        }
        else{
            listenerCheckBoxEdge = new CheckActionListener(
                    this.view.checkboxZero, this.view.checkboxOne, this.view.checkboxTwo,
                    this.view.checkboxThree, this.view.checkboxFour, this.view.checkboxFive, this.view.checkboxNo);
            this.view.checkboxZero.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxOne.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxTwo.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxThree.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxFour.addActionListener(listenerCheckBoxEdge);
            this.view.checkboxFive.addActionListener(listenerCheckBoxEdge);

        }
        this.view.checkboxNo.addActionListener(listenerCheckBoxEdge);
        CheckActionListener listenerCheckBoxPosition = new CheckActionListener(this.view.checkboxColumn,this.view.checkboxRow);
        this.view.checkboxColumn.addActionListener(listenerCheckBoxPosition);
        this.view.checkboxRow.addActionListener(listenerCheckBoxPosition);
        this.view.validate.addActionListener(e->createTileListener());
        this.view.goBackBtn.addActionListener(e -> goBackConfirmListener());

    }

    private void goBackConfirmListener() {
        NavigationController.getInstance(this.view.frame).navigateTo(this.view,this.editView);
    }


    private void createTileListener() {
        boolean filledPos = false;
        boolean filledEdge = false;
        for (JCheckBox box : this.view.checkListPosition) {
            if (box.isSelected()) {
                filledPos =true;
            }
        }
        for (JCheckBox check : this.view.checkListEdge) {
            if (check.isSelected()) {
                filledEdge = true;
            }
        }
        if (!filledPos || !filledEdge){
            JDialog dialog = new JDialog(this.frame, "Fill all Field", true);
            JLabel label = new JLabel("Before adding a new tile you need to fill every field.");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
            return;
        }
        ArrayList<Integer> edges = new ArrayList<Integer>() ;
        for (JCheckBox check : this.view.checkListEdge){
            if (check.isSelected()){
                String edge = check.getText();
                switch (edge){
                    case "Up":
                        edges.add(0);
                        break;
                    case "Right":
                        edges.add(1);
                        break;
                    case "Left":
                        edges.add(3);
                        break;
                    case "Upper Right":
                        edges.add(1);
                        break;
                    case "Down Right":
                        edges.add(2);
                        break;
                    case "Down":
                        if (!this.modification_board.isSquare()){
                            edges.add(3);
                        }else{edges.add(2);}
                        break;
                    case "Down Left":
                        edges.add(4);
                        break;
                    case "Upper Left":
                        edges.add(5);
                        break;
                    default:
                        break;
                }
            }
        }
        int last_id;
        if(this.modification_board.isEmptyBoard()){
            last_id = 0;
        }
        else{
            last_id = this.modification_board.lastTileAdded() +1;
        }


        Tile new_tile;
        switch ((String)this.view.selectRole.getSelectedItem()){
            case "SOURCE":
                new_tile = new Tile (last_id,0,0,this.shape, Role.SOURCE,edges);
                break;
            case "WIFI":
                new_tile = new Tile (last_id,0,0,this.shape,Role.WIFI,edges);
                break;
            case "LAMP":
                new_tile = new Tile (last_id,0,0,this.shape,Role.LAMP,edges);
                break;
            default:
                new_tile = new Tile (last_id,0,0,this.shape,Role.EMPTY,edges);
                break;
        }
        int [] pos = new int [2];
        for (JCheckBox box : this.view.checkListPosition){
            if (box.isSelected()){
                if (box.getText().equals("Right")){
                    pos = this.modification_board.getPosToAddRight();
                }
                else {
                    pos = this.modification_board.getPosToAddBottom();
                }
            }
        }
        new_tile.setPositionX(pos[0]);
        new_tile.setPositionY(pos[1]);
        this.modification_board.addTile(new_tile);
        this.modification_board.updateNeighbors();
        JDialog dialog = new JDialog(this.frame, "Tile Added", true);
        JLabel label = new JLabel("A Tile has been added!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(label);
        dialog.setSize(400, 100);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        this.editController.setModification_board(this.modification_board);
        navigationController.navigateTo(view,this.editView);
    }


}
