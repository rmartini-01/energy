package views;

import controllers.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class HomepageView extends JPanel {
    private JFrame frame;
    public HomepageView(JFrame frame ){
        this.frame = frame ;
        // Construction et injection de la barre de menu
       // this.setJMenuBar( this.createMenu() );
        JButton newGameBtn = new JButton("New game");
        newGameBtn.setBounds(275, 330, 150, 40);
        newGameBtn.addActionListener(this::newGameListener);
        addLogo();
        add(newGameBtn);


        //TODO bouton paramètres
        setVisible(true);

    }
    private void addLogo(){
        JLabel label = new JLabel(); //JLabel Creation
        label.setIcon(new ImageIcon("src/res/logo.png")); //Sets the image to be displayed as an icon
        Dimension size = label.getPreferredSize(); //Gets the size of the image
        label.setBounds(170, 130, size.width, size.height); //Sets the location of the image
       add(label); //Adds objects to the container

    }


    public void newGameListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( this, "Button clicked !" );
        NavigationController.getInstance(frame).navigateTo(this, new BoardView(frame));
    }

}
