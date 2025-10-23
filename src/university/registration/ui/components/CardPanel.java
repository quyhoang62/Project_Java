package university.registration.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardPanel extends JPanel {
    int radius=20;
    public CardPanel(){ setOpaque(false); setBorder(new EmptyBorder(28,36,28,36)); }
    @Override protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE); g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
        g2.setColor(new Color(220,220,220)); g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
        g2.dispose(); super.paintComponent(g);
    }
}
