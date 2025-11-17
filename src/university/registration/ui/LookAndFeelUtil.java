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
			// Table polish
			UIManager.put("Table.showHorizontalLines", Boolean.TRUE);
			UIManager.put("Table.showVerticalLines", Boolean.FALSE);
			UIManager.put("Table.intercellSpacing", new Dimension(0, 1));
			UIManager.put("Table.alternateRowColor", new Color(248, 250, 252));
			// Scrollbar
			UIManager.put("ScrollBar.trackArc", 999);
			UIManager.put("ScrollBar.thumbArc", 999);
			UIManager.put("ScrollBar.width", 14);
			// Focus/accent
			UIManager.put("Component.focusWidth", 1);
			UIManager.put("Component.innerFocusWidth", 0);
			UIManager.put("Button.focusedBackground", new Color(250,250,250));
			UIManager.put("Component.borderColor", new Color(220, 224, 230));
        } catch (Throwable ignore) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
        }
    }
}
