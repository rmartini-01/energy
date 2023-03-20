package views;

import controllers.NavigationController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBarView extends JMenuBar{
    private JFrame frame;
    public MenuBarView(JFrame frame){
    this.frame = frame;
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

        this.add(menuFile);
    }

    public void newGameListener( ActionEvent event ) {
       // JOptionPane.showMessageDialog( this, "Button clicked !" );
        NavigationController.getInstance(frame);
    }
    public void quitGameListener( ActionEvent event ) {
       //todo
    }
}
