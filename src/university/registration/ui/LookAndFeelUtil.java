package university.registration.util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class LookAndFeelUtil {
    public static void setupLookAndFeel() {
        try {
            Class<?> flatLight = Class.forName("com.formdev.flatlaf.themes.FlatMacLightLaf");
            flatLight.getMethod("setup").invoke(null);
            UIManager.put("Component.arc", 16);
            UIManager.put("Button.arc", 18);
            UIManager.put("TextComponent.arc", 16);
            UIManager.put("defaultFont", new FontUIResource("Segoe UI", Font.PLAIN, 18));
        } catch (Throwable ignore) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
        }
    }
}
