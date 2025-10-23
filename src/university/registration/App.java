package university.registration;

import university.registration.store.Memory;
import university.registration.ui.LoginFrame;
import university.registration.util.LookAndFeelUtil;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LookAndFeelUtil.setupLookAndFeel();
            Memory.init();
            new LoginFrame();
        });
    }
}
