import com.formdev.flatlaf.FlatDarculaLaf;
import controllers.BoardController;
import controllers.HomepageController;
import controllers.NavigationController;

import views.HomepageView;
import views.MenuBarView;
import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    public static void main(String[] args){
        FlatDarculaLaf.setup();
        JFrame frame = new JFrame(); // contains the main frame
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());
        HomepageController homepageController = new HomepageController(frame);
        NavigationController.getInstance(frame).navigateTo(null , homepageController.getView());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBarView(frame));
        frame.pack();
        frame.setVisible(true);
    }


}
