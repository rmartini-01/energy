package listeners;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckActionListener implements ActionListener {
    private JCheckBox check0;
    private JCheckBox check1;
    private JCheckBox check2;
    private JCheckBox check3;
    private JCheckBox check4;
    private JCheckBox check5;
    private JCheckBox checkNo;

    public CheckActionListener(JCheckBox checkC,JCheckBox checkR) {
        this.check0 = checkC;
        this.check1 = checkR;
    }

    public CheckActionListener(JCheckBox check0,
                               JCheckBox check1, JCheckBox check2,
                               JCheckBox check3, JCheckBox checkNo) {
        this.check0 = check0;
        this.check1 = check1;
        this.check2 = check2;
        this.check3 = check3;
        this.checkNo = checkNo;

    }

    public CheckActionListener(JCheckBox check0,
                               JCheckBox check1, JCheckBox check2,
                               JCheckBox check3, JCheckBox check4, JCheckBox check5,
                               JCheckBox checkNo) {
        this.check0 = check0;
        this.check1 = check1;
        this.check2 = check2;
        this.check3 = check3;
        this.check4 = check4;
        this.check5 = check5;
        this.checkNo = checkNo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.check2==null){
            if (check0.isSelected()) {
                check1.setSelected(false);
            }else{
                check0.setSelected(false);
            }
        }
        else {
            if (checkNo.isSelected()) {
                check0.setSelected(false);
                check1.setSelected(false);
                check2.setSelected(false);
                check3.setSelected(false);
                if (this.check4 != null) {
                    check4.setSelected(false);
                    check5.setSelected(false);
                }
            }
        }
    }
}
