package listeners;

import controllers.NavigationController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigateBackListener implements ActionListener {
    private JFrame jframe;

    public NavigateBackListener(JFrame frame) {
        this.jframe = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NavigationController.getInstance(jframe).goBack();
    }
}

