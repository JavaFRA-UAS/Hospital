package hospital.helper;

import java.awt.*;
import javax.swing.*;

public class JPanelWithBackground extends JPanel {

	private static final long serialVersionUID = -2804742567183773050L;
	private Paint painter;
	private Image image;
	private float alignmentX = 0.5f;
	private float alignmentY = 0.5f;
	private boolean isTransparentAdd = true;

	public JPanelWithBackground() {
		setLayout(new BorderLayout());
	}

	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	public void add(JComponent component) {
		add(component, null);
	}

	@Override
	public Dimension getPreferredSize() {
		if (image == null)
			return super.getPreferredSize();
		else
			return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	public void add(JComponent component, Object constraints) {
		if (isTransparentAdd) {
			makeComponentTransparent(component);
		}

		super.add(component, constraints);
	}

	public void setTransparentAdd(boolean isTransparentAdd) {
		this.isTransparentAdd = isTransparentAdd;
	}

	private void makeComponentTransparent(JComponent component) {
		component.setOpaque(false);

		if (component instanceof JScrollPane) {
			JScrollPane scrollPane = (JScrollPane) component;
			JViewport viewport = scrollPane.getViewport();
			viewport.setOpaque(false);
			Component c = viewport.getView();

			if (c instanceof JComponent) {
				((JComponent) c).setOpaque(false);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (painter != null) {
			Dimension d = getSize();
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(painter);
			g2.fill(new Rectangle(0, 0, d.width, d.height));
		}

		if (image != null) {
			Dimension d = getSize();
			g.drawImage(image, 0, 0, d.width, d.height, null);
		}
	}

}
