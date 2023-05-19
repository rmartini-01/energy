package views;

import controllers.NavigationController;
import listeners.NavigateBackListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBarView extends JMenuBar {
    private JFrame frame;
    private  HomepageView viewHome;
    public MenuBarView(JFrame frame,HomepageView viewHome){
        this.frame = frame;
        this.viewHome=viewHome;
        JMenu menuFile = new JMenu( "Game" );
        menuFile.setMnemonic( 'G' );

        JMenuItem menuSolution = new JMenuItem( "Solution" );
        menuSolution.setMnemonic( 'W' );
        menuSolution.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK) );
        menuSolution.addActionListener( this::solutionListener );
        menuFile.add(menuSolution);
        menuFile.addSeparator();


        JMenuItem menuQuit = new JMenuItem( "Quit game" );
        menuQuit.setMnemonic( 'Q' );
        menuQuit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK) );
        menuQuit.addActionListener( this::quitGameListener );
        menuFile.add(menuQuit);

        this.add(menuFile);
    }

    public void solutionListener( ActionEvent event ) {

    }
    public void quitGameListener( ActionEvent event ) {
        System.exit(0);
    }
}
