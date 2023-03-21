package views;

import listeners.NavigateBackListener;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private JFrame frame;
    public BoardView(JFrame frame){
        this.frame = frame;

        setName("Board");

        JButton goBackBtn = new JButton("Go back");
        goBackBtn.setBounds(275, 330, 150, 40);
        goBackBtn.addActionListener(new NavigateBackListener(frame));
        add(goBackBtn, BorderLayout.NORTH);
    }

}
