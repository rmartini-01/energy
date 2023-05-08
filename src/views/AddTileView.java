package views;

import controllers.AddTileController;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddTileView extends JPanel {
    public JFrame frame;
    public ArrayList<JCheckBox> checkListEdge;
    public ArrayList<JCheckBox> checkListPosition;
    public JComboBox<String> selectRole;
    public JCheckBox checkboxZero = new JCheckBox("Up");
    public JCheckBox checkboxOne = new JCheckBox("Right");
    public JCheckBox checkboxTwo = new JCheckBox("Down");
    public JCheckBox checkboxThree = new JCheckBox("Left");
    public JCheckBox checkboxFour = new JCheckBox("Down Left");
    public JCheckBox checkboxFive = new JCheckBox("Upper Left");
    public JCheckBox checkboxNo = new JCheckBox("No Edges");
    public JCheckBox checkboxRow = new JCheckBox("Row"); // add to length of board
    public JCheckBox checkboxColumn = new JCheckBox("Column"); //  add to width of board
    public JButton validate = new JButton ("Create new Tile");
    public Board modification_board;
    public Box verticalBox = Box.createVerticalBox();
    public Box verticalBoxEdges = Box.createVerticalBox();
    public Box horizontalBox2 = Box.createHorizontalBox();
    public Box horizontalBox = Box.createHorizontalBox();


    public AddTileView(JFrame frame,int level,char shape, Board modification_board) {
        this.modification_board=modification_board;
        this.frame = frame;
        this.checkListEdge=new ArrayList<JCheckBox>() ;
        this.checkListPosition=new ArrayList<JCheckBox>() ;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setName("Edit Mode Level " + level + " -  Add a Tile");
        horizontalBox.add(Box.createHorizontalGlue());
        verticalBox.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);
        JLabel label_edges = new JLabel("Choose Edges :");
        this.checkListEdge.add(checkboxZero);
        this.checkListEdge.add(checkboxOne);
        this.checkListEdge.add(checkboxTwo);
        this.checkListEdge.add(checkboxThree);
        if (shape == 'S'){
            this.checkListEdge.add(checkboxNo);
            horizontalBox2.add(label_edges);
            horizontalBox2.setAlignmentX(CENTER_ALIGNMENT);
            horizontalBox2.setAlignmentY(CENTER_ALIGNMENT);
            verticalBox.add(horizontalBox2);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 20)));
            verticalBoxEdges.add(checkboxZero);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxOne);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxTwo);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxThree);
        }else{
            this.checkListEdge.add(checkboxFour);
            this.checkListEdge.add(checkboxFive);
            this.checkListEdge.add(checkboxNo);
            horizontalBox2.add(label_edges);
            verticalBox.add(horizontalBox2);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 20)));
            verticalBoxEdges.add(checkboxZero);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxOne);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxTwo);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxThree);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxFour);
            verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBoxEdges.add(checkboxFive);
        }
        verticalBoxEdges.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalBoxEdges.add(checkboxNo);
        verticalBox.add(verticalBoxEdges);
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
        this.checkListPosition.add(checkboxRow);
        this.checkListPosition.add(checkboxColumn);
        horizontalBox3.add(label_position);
        horizontalBox3.add(Box.createRigidArea(new Dimension(40, 10)));
        horizontalBox3.add(checkboxRow);
        horizontalBox3.add(Box.createRigidArea(new Dimension(20, 10)));
        horizontalBox3.add(checkboxColumn);
        verticalBox.add(horizontalBox3);
        verticalBox.add(Box.createRigidArea(new Dimension(0, 30)));
        validate.setPreferredSize(new Dimension(150, 40));
        validate.setMaximumSize(new Dimension(150, 40));
        validate.setAlignmentX(CENTER_ALIGNMENT);
        validate.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.add(horizontalBox);
        verticalBox.add(Box.createRigidArea(new Dimension(10, 50)));
        verticalBox.add(validate);
        add(verticalBox);
        setVisible(true);
    }





}
