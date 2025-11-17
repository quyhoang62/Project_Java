package university.registration.ui.components;

import javax.swing.*;
import java.awt.*;

public class AguBadgePanel extends JPanel {
	public AguBadgePanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(120, 120));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int size = Math.min(getWidth(), getHeight());
		int x = (getWidth() - size) / 2;
		int y = (getHeight() - size) / 2;

		g2.setColor(new Color(253, 219, 17));
		g2.fillOval(x, y, size, size);

		g2.setStroke(new BasicStroke(4f));
		g2.setColor(new Color(0, 78, 146));
		g2.drawOval(x + 2, y + 2, size - 4, size - 4);

		int inner = (int) (size * 0.75);
		int ix = (getWidth() - inner) / 2;
		int iy = (getHeight() - inner) / 2;
		g2.setColor(Color.WHITE);
		g2.fillOval(ix, iy, inner, inner);
		g2.setColor(new Color(0, 78, 146));
		g2.drawOval(ix, iy, inner, inner);

		g2.setColor(new Color(0, 155, 101));
		int[] ax = {ix + inner / 2, ix + inner - 10, ix + inner - 10, ix + inner / 2};
		int[] ay = {iy + 12, iy + inner / 2, iy + inner - 12, iy + inner / 2 + 20};
		g2.fillPolygon(ax, ay, 4);

		g2.setColor(new Color(0, 78, 146));
		g2.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.drawPolyline(new int[]{ix + 12, ix + inner / 2, ix + inner / 2, ix + inner - 12},
				new int[]{iy + inner - 12, iy + inner - 12, iy + 12, iy + 12}, 4);

		g2.dispose();
		super.paintComponent(g);
	}
}

