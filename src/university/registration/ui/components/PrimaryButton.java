package university.registration.ui.components;

import javax.swing.*;
import java.awt.*;

public class PrimaryButton extends JButton {
	private final int arc = 22;
	private final Color accentTop = new Color(0, 168, 181);
	private final Color accentBottom = new Color(0, 108, 196);

	public PrimaryButton(String text) {
		super(text);
		setForeground(Color.WHITE);
		setFont(new Font("Segoe UI", Font.BOLD, 20));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth(), h = getHeight();
		GradientPaint gp = new GradientPaint(0, 0, accentTop, 0, h, accentBottom);
		g2.setPaint(gp);
		g2.fillRoundRect(0, 0, w, h, arc, arc);
		g2.setColor(new Color(0, 89, 158));
		g2.drawRoundRect(0, 0, w, h, arc, arc);
		g2.dispose();
		super.paintComponent(g);
	}
}

