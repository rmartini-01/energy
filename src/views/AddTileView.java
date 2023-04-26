package views;

import controllers.NavigationController;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddTileView extends JPanel {
    private JFrame frame;
    private final int level;
    private Board modification_board;
    private ArrayList<JCheckBox> checkListEdge;
    private ArrayList<JCheckBox> checkListPosition;
    private JComboBox<String> selectRole;
    private char shape;
    //private int id;


    public AddTileView(JFrame frame, int level ,Board modif_board ) {
        this.level = level;
        this.frame = frame;

        this.modification_board = modif_board;

        this.checkListEdge=new ArrayList<JCheckBox>() ;
        this.checkListPosition=new ArrayList<JCheckBox>() ;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setName("Edit Mode Level " + level + " -  Add a Tile");
        this.shape = (this.modification_board.isSquare()) ? 'S' : 'H';
        //this.id = this.modification_board.getBoard().size() ; // id starts at 0 ?
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalGlue());
        Box verticalBox = Box.createVerticalBox();
        verticalBox.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);

        JLabel label_edges = new JLabel("Choose Edges :");

        if (shape == 'S'){
            JCheckBox checkboxZero = new JCheckBox("Up");
            JCheckBox checkboxOne = new JCheckBox("Right");
            JCheckBox checkboxTwo = new JCheckBox("Down");
            JCheckBox checkboxThree = new JCheckBox("Left");
            JCheckBox checkboxNo = new JCheckBox("No Edges");
            this.checkListEdge.add(checkboxZero);
            this.checkListEdge.add(checkboxOne);
            this.checkListEdge.add(checkboxTwo);
            this.checkListEdge.add(checkboxThree);
            this.checkListEdge.add(checkboxNo);
            Box horizontalBox2 = Box.createHorizontalBox();
            horizontalBox2.add(label_edges);
            horizontalBox2.setAlignmentX(CENTER_ALIGNMENT);
            horizontalBox2.setAlignmentY(CENTER_ALIGNMENT);
            verticalBox.add(horizontalBox2);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 20)));
            verticalBox.add(checkboxZero);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxOne);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxTwo);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxThree);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxNo);
            CheckActionListener listenerCheckBoxEdge = new CheckActionListener(
                    checkboxZero,checkboxOne,checkboxTwo,checkboxThree,checkboxNo);
            checkboxZero.addActionListener(listenerCheckBoxEdge);
            checkboxOne.addActionListener(listenerCheckBoxEdge);
            checkboxTwo.addActionListener(listenerCheckBoxEdge);
            checkboxThree.addActionListener(listenerCheckBoxEdge);
            checkboxNo.addActionListener(listenerCheckBoxEdge);

        }else{
            JCheckBox checkboxZero = new JCheckBox("Up");
            JCheckBox checkboxOne = new JCheckBox("Upper Right");
            JCheckBox checkboxTwo = new JCheckBox("Down Right");
            JCheckBox checkboxThree = new JCheckBox("Down");
            JCheckBox checkboxFour = new JCheckBox("Down Left");
            JCheckBox checkboxFive = new JCheckBox("Upper Left");
            JCheckBox checkboxNo = new JCheckBox("No Edges");
            this.checkListEdge.add(checkboxZero);
            this.checkListEdge.add(checkboxOne);
            this.checkListEdge.add(checkboxTwo);
            this.checkListEdge.add(checkboxThree);
            this.checkListEdge.add(checkboxFour);
            this.checkListEdge.add(checkboxFive);
            this.checkListEdge.add(checkboxNo);
            Box horizontalBox2 = Box.createHorizontalBox();
            horizontalBox2.add(label_edges);
            verticalBox.add(horizontalBox2);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 20)));
            verticalBox.add(checkboxZero);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxOne);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxTwo);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxThree);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxFour);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxFive);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(checkboxNo);
            CheckActionListener listenerCheckBoxEdge = new CheckActionListener(
                    checkboxZero,checkboxOne,checkboxTwo,checkboxThree,checkboxFour,checkboxFive,checkboxNo);
            checkboxZero.addActionListener(listenerCheckBoxEdge);
            checkboxOne.addActionListener(listenerCheckBoxEdge);
            checkboxTwo.addActionListener(listenerCheckBoxEdge);
            checkboxThree.addActionListener(listenerCheckBoxEdge);
            checkboxFour.addActionListener(listenerCheckBoxEdge);
            checkboxFive.addActionListener(listenerCheckBoxEdge);
            checkboxNo.addActionListener(listenerCheckBoxEdge);
        }

        verticalBox.add(Box.createRigidArea(new Dimension(0, 50)));
        JLabel label_role = new JLabel("Choose a role :");
        this.selectRole = new JComboBox<>();
        selectRole.addItem("EMPTY");
        selectRole.addItem("WIFI");
        selectRole.addItem("LAMP");
        selectRole.addItem("SOURCE");

        selectRole.setPreferredSize(new Dimension(150, 40));
        selectRole.setMaximumSize(new Dimension(150, 40));
        horizontalBox.add(label_role);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontalBox.add(selectRole);
        horizontalBox.add(Box.createHorizontalGlue());

        Box horizontalBox3 = Box.createHorizontalBox();
        JLabel label_position = new JLabel("Add to :");
        JCheckBox checkboxRow = new JCheckBox("Row"); // add to length of board
        JCheckBox checkboxColumn = new JCheckBox("Column"); //  add to width of board
        this.checkListPosition.add(checkboxRow);
        this.checkListPosition.add(checkboxColumn);
        horizontalBox3.add(label_position);
        horizontalBox3.add(Box.createRigidArea(new Dimension(40, 10)));
        horizontalBox3.add(checkboxRow);
        horizontalBox3.add(Box.createRigidArea(new Dimension(20, 10)));
        horizontalBox3.add(checkboxColumn);
        verticalBox.add(horizontalBox3);
        verticalBox.add(Box.createRigidArea(new Dimension(0, 30)));
        CheckActionListener listenerCheckBoxPosition = new CheckActionListener(checkboxColumn,checkboxRow);
        checkboxColumn.addActionListener(listenerCheckBoxPosition);
        checkboxRow.addActionListener(listenerCheckBoxPosition);



        JButton validate = new JButton ("Create new Tile");
        validate.setPreferredSize(new Dimension(150, 40));
        validate.setMaximumSize(new Dimension(150, 40));
        validate.addActionListener(this::createTileListener);
        validate.setAlignmentX(CENTER_ALIGNMENT);
        validate.setAlignmentY(CENTER_ALIGNMENT);

        verticalBox.add(horizontalBox);
        verticalBox.add(Box.createRigidArea(new Dimension(10, 50)));
        verticalBox.add(validate);
        add(verticalBox);
        setVisible(true);
    }


    private void createTileListener(ActionEvent event) {
        boolean filledPos = false;
        boolean filledEdge = false;
        for (JCheckBox box : this.checkListPosition) {
            if (box.isSelected()) {
                filledPos =true;
            }
        }
        for (JCheckBox check : this.checkListEdge) {
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
        for (JCheckBox check : this.checkListEdge){
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

        Tile last_tileAdded= this.modification_board.lastTileAdded();
        int [] pos = new int [2];
        for (JCheckBox box : this.checkListPosition){
            if (box.isSelected()){
                if (box.getText().equals("Column")){
                   pos = this.modification_board.getPosToAddColumn();
                }
                else {
                    pos = this.modification_board.getPosToAddRow();
                }
            }
        }
        int id = last_tileAdded.getId()+1;
        Tile new_tile;
        switch ((String)this.selectRole.getSelectedItem()){
            case "SOURCE":
                new_tile = new Tile (id,pos[0],pos[1],this.shape,Role.SOURCE,edges);
                break;
            case "WIFI":
                new_tile = new Tile (id,pos[0],pos[1],this.shape,Role.WIFI,edges);
                break;
            case "LAMP":
                new_tile = new Tile (id,pos[0],pos[1],this.shape,Role.LAMP,edges);
                break;
            default:
                new_tile = new Tile (id,pos[0],pos[1],this.shape,Role.EMPTY,edges);
                break;
        }
        this.modification_board.getBoard().add(new_tile);
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
        NavigationController.getInstance(frame).navigateTo(this,
                new EditGameView(this.frame, this.level,this.modification_board));
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
