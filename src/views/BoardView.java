package views;
import listeners.NavigateBackListener;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardView extends BoardAbstract {
    public JDialog dialog;
    public JButton btnDialog;
    public JButton nextLevelBtn;
    public BoardView(JFrame frame, Level level, Board board) {
        this.frame = frame;
        System.out.println("cons");
        this.level = level;
        this.board = board;
        this.tileViews = new ArrayList<>();
        this.btnDialog = new JButton("OK");
        this.nextLevelBtn = new JButton("Next level");
        setName("Board");
        add(goBackBtn());
    }


    public JPanel getPanel() {
        return this;
    }

    public void showWinningDialog(Component parentComponent) {
        dialog = new JDialog();
        dialog.setTitle("Congratulations");
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        JLabel l = new JLabel("The circuit is fully connected !");
        JPanel p = new JPanel();
        p.add(l);
        p.add(btnDialog);
        p.add(nextLevelBtn);
        dialog.add(p);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }

    public void showAlert(Component parentComponent, String message) {
        dialog = new JDialog();
        dialog.setTitle("Alert");
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        JLabel l = new JLabel(message);
        JPanel p = new JPanel();
        p.add(l);
        p.add(btnDialog);
        dialog.add(p);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }
    private JButton goBackBtn() {
        JButton goBackBtn = new JButton();
        ImageIcon icon = new ImageIcon("src/res/back-icon-white.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        goBackBtn.setIcon(scaledIcon);
        goBackBtn.setPreferredSize(new Dimension(25, 25));
        goBackBtn.addActionListener(new NavigateBackListener(frame, getPanel()));
        goBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return goBackBtn;
    }

}