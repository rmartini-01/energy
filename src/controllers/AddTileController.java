package controllers;

import models.Board;
import models.Role;
import models.Tile;
import views.AddTileView;
import views.EditGameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        System.out.println("Before adding");
        if (!this.modification_board.getBoard().isEmpty()){
            for (Tile t : this.modification_board.getBoard()){
                t.printTile();
            }
        }
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
        int last_id = this.modification_board.lastTileAdded() +1;
        int [] pos = new int [2];
        for (JCheckBox box : this.view.checkListPosition){
            if (box.isSelected()){
                if (box.getText().equals("Column")){
                    pos = this.modification_board.getPosToAddColumn();
                }
                else {
                    pos = this.modification_board.getPosToAddRow();
                }
            }
        }
        Tile new_tile;
        switch ((String)this.view.selectRole.getSelectedItem()){
            case "SOURCE":
                new_tile = new Tile (last_id,pos[0],pos[1],this.shape, Role.SOURCE,edges);
                break;
            case "WIFI":
                new_tile = new Tile (last_id,pos[0],pos[1],this.shape,Role.WIFI,edges);
                break;
            case "LAMP":
                new_tile = new Tile (last_id,pos[0],pos[1],this.shape,Role.LAMP,edges);
                break;
            default:
                new_tile = new Tile (last_id,pos[0],pos[1],this.shape,Role.EMPTY,edges);
                break;
        }
        this.modification_board.addTile(new_tile);
        System.out.println("After adding");
        for (Tile t : this.modification_board.getBoard()){
            t.printTile();
        }
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


    class CheckActionListener implements ActionListener {
        private JCheckBox check0;
        private JCheckBox check1;
        private JCheckBox check2;
        private JCheckBox check3;
        private JCheckBox check4;
        private JCheckBox check5;
        private JCheckBox checkNo;

        public CheckActionListener(JCheckBox checkC,JCheckBox checkR) {
            this.check0 = checkC;
            this.check1 = checkR;
        }

        public CheckActionListener(JCheckBox check0,
                                   JCheckBox check1, JCheckBox check2,
                                   JCheckBox check3, JCheckBox checkNo) {
            this.check0 = check0;
            this.check1 = check1;
            this.check2 = check2;
            this.check3 = check3;
            this.checkNo = checkNo;

        }

        public CheckActionListener(JCheckBox check0,
                                   JCheckBox check1, JCheckBox check2,
                                   JCheckBox check3, JCheckBox check4, JCheckBox check5,
                                   JCheckBox checkNo) {
            this.check0 = check0;
            this.check1 = check1;
            this.check2 = check2;
            this.check3 = check3;
            this.check4 = check4;
            this.check5 = check5;
            this.checkNo = checkNo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.check2==null){
                if (check0.isSelected()) {
                    check1.setSelected(false);
                }else{
                    check0.setSelected(false);
                }
            }
            else {
                if (checkNo.isSelected()) {
                    check0.setSelected(false);
                    check1.setSelected(false);
                    check2.setSelected(false);
                    check3.setSelected(false);
                    if (this.check4 != null) {
                        check4.setSelected(false);
                        check5.setSelected(false);
                    }
                }
            }
        }
    }
}
