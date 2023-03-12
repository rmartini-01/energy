import com.formdev.flatlaf.FlatDarculaLaf;
import views.HomepageView;

import javax.swing.*;


public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        FlatDarculaLaf.setup();

        HomepageView home = new HomepageView();
    }
}
