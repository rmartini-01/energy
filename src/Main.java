import views.Homepage;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(600,400);
        mainFrame.setLocationRelativeTo( null );
        mainFrame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        Homepage home = new Homepage(mainFrame);
        mainFrame.setVisible(true);
    }
}
