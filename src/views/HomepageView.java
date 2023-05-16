package views;


import javax.swing.*;
import java.awt.*;



public class HomepageView extends JPanel {
    private JButton newGameBtn;
    private JButton settingsBtn;

    private JButton editGameBtn;

    JComboBox<Integer> selectLevel = new JComboBox<>();
    public HomepageView() {
        setName("Home");

        newGameBtn = new JButton("Start game");
        settingsBtn = new JButton("Settings");
        editGameBtn = new JButton("Edit mode");

       /*     newGameBtn.addActionListener(e -> controller.newGameAction());
            settings.addActionListener(e -> controller.settingsAction());
            editGameBtn.addActionListener(e -> controller.editGameAction());*/

        JLabel level = new JLabel("Select a level: "); //JLabel Creation Box
        for (int i = 1; i < 12; i++) {
            selectLevel.addItem(i);
        }
        newGameBtn.setPreferredSize(new Dimension(150, 40));
        newGameBtn.setMaximumSize(new Dimension(150, 40));
        selectLevel.setPreferredSize(new Dimension(150, 40));
        selectLevel.setMaximumSize(new Dimension(150, 40));
        Box verticalBox = Box.createVerticalBox();
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalGlue());
        horizontalBox.add(level);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontalBox.add(selectLevel);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));

        //edit mode
        editGameBtn.setPreferredSize(new Dimension(150, 40));
        editGameBtn.setMaximumSize(new Dimension(150, 40));
        Box horizontalBoxEditMode = Box.createHorizontalBox();
        horizontalBoxEditMode.add(Box.createHorizontalGlue());
        horizontalBoxEditMode.add(editGameBtn);
        horizontalBoxEditMode.add(Box.createHorizontalGlue());
        verticalBox.add(horizontalBoxEditMode);

        Box verticalBoxButtons = Box.createVerticalBox();
        verticalBoxButtons.add(newGameBtn);
        verticalBoxButtons.add(editGameBtn);
        horizontalBox.add(verticalBoxButtons);

        horizontalBox.add(Box.createHorizontalGlue());


        add(Box.createVerticalGlue());
        verticalBox.add(addLogo());
        verticalBox.add(horizontalBox);
        verticalBox.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);

        add(verticalBox);
        //TODO bouton paramètres
        setVisible(true);
    }


    private JLabel addLogo() {
        JLabel logo = new JLabel(); //JLabel Creation
        logo.setIcon(new ImageIcon("src/res/logo.png")); //Sets the image to be displayed as an icon
        logo.setMaximumSize(logo.getPreferredSize());
        return logo;
    }

    public int getSelectedLevel() {
        return (int) selectLevel.getSelectedItem();
    }


    public JButton getNewGameBtn() {
        return newGameBtn;
    }

    public JButton getSettings() {
        return settingsBtn;
    }

    public JButton getEditGameBtn() {
        return editGameBtn;
    }


}
