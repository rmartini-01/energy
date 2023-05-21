package views;

import models.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModifyTileView extends JPanel {
    public JFrame frame;
    public ArrayList<JCheckBox> checkListEdge;
    public JComboBox<String> selectRole;
    public JCheckBox checkboxZero = new JCheckBox("Up");
    public JCheckBox checkboxOne = new JCheckBox("Right");
    public JCheckBox checkboxTwo = new JCheckBox("Down");
    public JCheckBox checkboxThree = new JCheckBox("Left");
    public JCheckBox checkboxFour = new JCheckBox("Down Left");
    public JCheckBox checkboxFive = new JCheckBox("Upper Left");
    public JCheckBox checkboxNo = new JCheckBox("No Edges");
    public JButton validate = new JButton ("Modify Tile");
    public Board modification_board;
    public Box verticalBox = Box.createVerticalBox();
    public Box verticalBoxEdges = Box.createVerticalBox();
    public Box horizontalBox2 = Box.createHorizontalBox();
    public Box horizontalBox = Box.createHorizontalBox();
    public Level level;
    public JButton goBackBtn = new JButton();
    public int id_tile;

    public ModifyTileView(JFrame frame,Level level,char shape, Board modification_board, int id_tile) {
        this.level=level;
        this.id_tile=id_tile;
        this.modification_board=modification_board;
        this.frame = frame;
        this.checkListEdge=new ArrayList<JCheckBox>() ;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setName("Edit Mode Level " + level.getNum() + " - Modify Tile "+ id_tile);
        horizontalBox.add(Box.createHorizontalGlue());
        verticalBox.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);
        ImageIcon icon = new ImageIcon("src/res/back-icon-white.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.goBackBtn.setIcon(scaledIcon);
        this.goBackBtn.setPreferredSize(new Dimension(25, 25));
        this.goBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        horizontalBox.add(Box.createHorizontalGlue());
        verticalBox.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);
        verticalBox.add(this.goBackBtn);
        verticalBox.add(Box.createRigidArea(new Dimension(0, 50)));
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
        verticalBox.add(horizontalBox);
        verticalBox.add(Box.createRigidArea(new Dimension(10, 50)));
        verticalBox.add(validate);
        add(verticalBox);
        setVisible(true);
    }

    public void setId_tile(int id_tile) {
        this.id_tile = id_tile;
    }

}


