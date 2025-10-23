package university.registration.ui.components;

import javax.swing.*;
import java.awt.*;

public class NeutralButton extends JButton {
    private final int arc = 18;
    public NeutralButton(String text){
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setForeground(Color.BLACK);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10,22,10,22));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(180,44));
        putClientProperty("JButton.buttonType", null);
    }
    @Override protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w=getWidth(),h=getHeight();
        g2.setColor(Color.WHITE); g2.fillRoundRect(0,0,w-1,h-1,arc,arc);
        g2.setColor(new Color(180,180,180)); g2.drawRoundRect(0,0,w-1,h-1,arc,arc);
        g2.dispose(); super.paintComponent(g);
    }
}
