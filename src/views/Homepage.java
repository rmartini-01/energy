package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Homepage{
    JFrame frame;
    public Homepage(JFrame frame ){
        this.frame = frame ;
        // Construction et injection de la barre de menu
        this.frame.setJMenuBar( this.createMenu() );
    }

    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu( "Game" );
        menuFile.setMnemonic( 'G' );

        JMenuItem menuNewGame = new JMenuItem( "New game" );
        menuNewGame.setMnemonic( 'N' );
        menuNewGame.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
        menuNewGame.addActionListener( this::menuNewListener );
        menuFile.add(menuNewGame);
        menuFile.addSeparator();

        menuBar.add(menuFile);
        return menuBar;
    }

    public void menuNewListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( frame, "Button clicked !" );
    }
}
