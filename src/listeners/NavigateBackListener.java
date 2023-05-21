package listeners;

import controllers.HomepageController;
import controllers.NavigationController;
import views.HomepageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigateBackListener implements ActionListener {
    private JFrame jframe;
    private JPanel view;

    public NavigateBackListener(JFrame frame, JPanel view) {
        this.jframe = frame;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HomepageView homepageView = new HomepageView();
        HomepageController homepageController = new HomepageController(jframe, homepageView);
        NavigationController.getInstance(jframe).navigateTo(view, homepageView );
    }
}

