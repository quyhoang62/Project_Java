package university.registration.ui.components;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
	public BackgroundPanel() { setOpaque(false); setLayout(new BorderLayout()); }
	@Override protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth(), h = getHeight();
		Color top = new Color(245, 247, 250);
		Color bottom = Color.WHITE;
		GradientPaint gp = new GradientPaint(0, 0, top, 0, h, bottom);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h);
		g2.dispose();
		super.paintComponent(g);
	}
}



