package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class HomepageView extends JFrame {
    Container c = this.getContentPane();

    public void setupFrame(){
        setTitle("Energy");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

    }

    public HomepageView(){
        setupFrame();
        // Construction et injection de la barre de menu
        this.setJMenuBar( this.createMenu() );
        JButton newGameBtn = new JButton("New game");
        newGameBtn.setBounds(275, 330, 150, 40);
        newGameBtn.addActionListener(this::newGameListener);
        addLogo();
        add(newGameBtn);
        setVisible(true);

    }
    private void addLogo(){
        JLabel label = new JLabel(); //JLabel Creation
        label.setIcon(new ImageIcon("src/res/logo.png")); //Sets the image to be displayed as an icon
        Dimension size = label.getPreferredSize(); //Gets the size of the image
        label.setBounds(170, 130, size.width, size.height); //Sets the location of the image
       add(label); //Adds objects to the container

    }
    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu( "Game" );
        menuFile.setMnemonic( 'G' );

        JMenuItem menuNewGame = new JMenuItem( "New game" );
        menuNewGame.setMnemonic( 'N' );
        menuNewGame.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
        menuNewGame.addActionListener( this::newGameListener );
        menuFile.add(menuNewGame);
        menuFile.addSeparator();


        JMenuItem menuQuit = new JMenuItem( "Quit game" );
        menuQuit.setMnemonic( 'Q' );
        menuQuit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK) );
        menuQuit.addActionListener( this::quitGameListener );
        menuFile.add(menuQuit);

        menuBar.add(menuFile);
        return menuBar;
    }

    public void newGameListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( this, "Button clicked !" );
    }
    public void quitGameListener( ActionEvent event ) {
        dispose();
    }

}
