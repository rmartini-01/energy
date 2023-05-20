package controllers;

import models.Board;
import listeners.*;
import models.Role;
import models.Tile;
import views.EditGameView;
import views.ModifyTileView;

import javax.swing.*;
import java.util.ArrayList;

public class ModifyTileController extends Controller {
        private EditmodeController editController;
        private EditGameView editView;
        private final ModifyTileView view;
        private JFrame frame;
        private Board modification_board;
        private char shape;

        public ModifyTileController(JFrame frame , Board modif_board, ModifyTileView view, EditGameView editView, EditmodeController editController, NavigationController nc ) {
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
            this.view.goBackBtn.addActionListener(e -> goBackConfirmListener());
            this.view.checkboxNo.addActionListener(listenerCheckBoxEdge);
            this.view.validate.addActionListener(e->modifyTileListener());
        }

        private void modifyTileListener() {
            boolean filledEdge = false;
            for (JCheckBox check : this.view.checkListEdge) {
                if (check.isSelected()) {
                    filledEdge = true;
                }
            }
            if (!filledEdge) {
                JDialog dialog = new JDialog(this.frame, "Fill all Field", true);
                JLabel label = new JLabel("Before modifying a tile you need to fill every field.");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                dialog.add(label);
                dialog.setSize(400, 100);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
                return;
            }
            ArrayList<Integer> edges = new ArrayList<Integer>();
            for (JCheckBox check : this.view.checkListEdge) {
                if (check.isSelected()) {
                    String edge = check.getText();
                    switch (edge) {
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
                            if (!this.modification_board.isSquare()) {
                                edges.add(3);
                            } else {
                                edges.add(2);
                            }
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

            Role r;
            switch ((String) this.view.selectRole.getSelectedItem()) {
                case "SOURCE":
                    r = Role.SOURCE;
                    break;
                case "WIFI":
                    r = Role.WIFI;
                    break;
                case "LAMP":
                    r = Role.LAMP;
                    break;
                default:
                    r = Role.EMPTY;
                    break;
            }
            this.modification_board.modifyTile(this.view.id_tile, r, edges);
            this.modification_board.updateNeighbors();

            JDialog dialog = new JDialog(this.frame, "Tile Modified", true);
            JLabel label = new JLabel("Tile " + this.view.id_tile + " has been modified!");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dialog.add(label);
            dialog.setSize(400, 100);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
            this.editController.setModification_board(this.modification_board);
            navigationController.navigateTo(view, this.editView);

        }

    private void goBackConfirmListener() {
        NavigationController.getInstance(this.view.frame).navigateTo(this.view,this.editView);
    }

}

