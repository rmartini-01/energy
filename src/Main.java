import com.formdev.flatlaf.FlatDarculaLaf;
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
        frame.setPreferredSize(new Dimension(700, 900));
        NavigationController navigationController = NavigationController.getInstance(frame);
        HomepageView homepageView = new HomepageView();
        HomepageController homepageController = new HomepageController(homepageView);
        homepageController.setNavigationController(navigationController);
        navigationController.navigateTo(null , homepageController.getView());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setJMenuBar(new MenuBarView(frame));
        frame.pack();
        frame.setVisible(true);
    }


}
