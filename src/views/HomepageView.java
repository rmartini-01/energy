package views;

import controllers.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomepageView extends JPanel {
    private JFrame frame;
    public HomepageView(JFrame frame ){
        this.frame = frame ;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JButton newGameBtn = new JButton("Start game");
        newGameBtn.addActionListener(this::newGameListener);
        JButton settings = new JButton("Settings");
        settings.addActionListener(this::settingsListener);

        JComboBox<Integer> selectLevel= new JComboBox<>();
        for(int i = 1; i<12; i++){
            selectLevel.addItem(i);
        }
        selectLevel.setPreferredSize(new Dimension(150, 40));
        selectLevel.setMaximumSize(new Dimension(150, 40));
        newGameBtn.setPreferredSize(new Dimension(150, 40));
        newGameBtn.setMaximumSize(new Dimension(150, 40));


        JLabel level = new JLabel("Select a level: "); //JLabel Creation
        Box verticalBox = Box.createVerticalBox();
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalGlue());
        horizontalBox.add(level);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontalBox.add(selectLevel);
        horizontalBox.add(Box.createRigidArea(new Dimension(10, 0)));
        horizontalBox.add(newGameBtn);
        horizontalBox.add(Box.createHorizontalGlue());

        horizontalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        verticalBox.add(addLogo());
        verticalBox.add(horizontalBox);
        verticalBox.setAlignmentY(CENTER_ALIGNMENT);
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);

        add(verticalBox);
        //TODO bouton paramètres
        setVisible(true);

    }

    private JLabel addLogo(){
        JLabel logo = new JLabel(); //JLabel Creation
        logo.setIcon(new ImageIcon("src/res/logo.png")); //Sets the image to be displayed as an icon
        logo.setMaximumSize(logo.getPreferredSize());
        return logo;
    }
    public void newGameListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( this, "Button clicked !" );
        NavigationController.getInstance(frame).navigateTo(this, new BoardView(frame));
    }

    public void settingsListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( this, "Button clicked !" );
        //NavigationController.getInstance(frame).navigateTo(this, new SettingsView(frame));
    }
}
