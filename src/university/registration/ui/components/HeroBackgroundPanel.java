package university.registration.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class HeroBackgroundPanel extends JPanel {
	private final BufferedImage texture;

	public HeroBackgroundPanel() {
		setOpaque(false);
		setLayout(new BorderLayout());
		texture = createTexture();
	}

	private BufferedImage createTexture() {
		int width = 1920;
		int height = 1080;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gp = new GradientPaint(0, 0, new Color(8, 86, 145), 0, height, new Color(3, 130, 92));
		g2.setPaint(gp);
		g2.fillRect(0, 0, width, height);

		Random rnd = new Random("BACHKHOA".hashCode());
		Color[] palette = {
				new Color(255, 211, 0, 140),
				new Color(0, 173, 181, 130),
				new Color(255, 255, 255, 120),
				new Color(52, 138, 199, 120)
		};

		for (int i = 0; i < 18; i++) {
			int size = 200 + rnd.nextInt(260);
			int x = rnd.nextInt(width + size) - size / 2;
			int y = rnd.nextInt(height + size) - size / 2;
			Color base = palette[rnd.nextInt(palette.length)];
			RadialGradientPaint rg = new RadialGradientPaint(
					new Point2D.Float(x + size / 2f, y + size / 2f),
					size / 2f,
					new float[]{0f, 1f},
					new Color[]{new Color(base.getRed(), base.getGreen(), base.getBlue(), 160), new Color(base.getRed(), base.getGreen(), base.getBlue(), 0)}
			);
			g2.setPaint(rg);
			g2.fillOval(x, y, size, size);
		}

		g2.dispose();
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(texture, 0, 0, getWidth(), getHeight(), this);
		g2.setColor(new Color(0, 23, 41, 140));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
		super.paintComponent(g);
	}
}

