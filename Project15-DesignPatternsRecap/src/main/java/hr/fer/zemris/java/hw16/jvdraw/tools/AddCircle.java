package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;

/**
 * Class that implements Tool and represents concrete
 * state in state design pattern. This state is responsible
 * for drawing circle on the JDrawingCanvas. On first
 * mouse click center of the circle calculated and
 * with second mouse click that circle will be added to the
 * model. 
 * 
 * @author Luka Grgić
 */
public class AddCircle implements Tool {

	/**
	 * Center x coordinate.
	 */
	private Integer x0;
	/**
	 * Center y coordinate.
	 */
	private Integer y0;
	/**
	 * Radius.
	 */
	private Integer radius;
	/**
	 * Flag that tells if first click has been made.
	 */
	private boolean isClicked;
	/**
	 * Provides color for drawing the circle.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Model that stores all geometrical objects.
	 */
	private DrawingModel model;
	
	/**
	 * Constructor.
	 * 
	 * @param fgColorProvider provides for drawing the circle.
	 * @param model model that stores all geometrical objects.
	 */
	public AddCircle(IColorProvider fgColorProvider, DrawingModel model) {
		this.isClicked = false;
		this.fgColorProvider = fgColorProvider;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isClicked) {
			x0 = e.getX();
			y0 = e.getY();
			isClicked = true;
		} else {
			model.add(new Circle(x0, y0, radius, fgColorProvider.getCurrentColor()));
			x0 = y0 = radius = null;
			isClicked = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		calculateRadius(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		calculateRadius(e);
	}

	/**
	 * Calculates radius.
	 * 
	 * @param e event
	 */
	private void calculateRadius(MouseEvent e) {
		if (x0 != null) {
			int x1 = e.getX();
			int y1 = e.getY();
			radius = (int) Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(fgColorProvider.getCurrentColor());
		
		if (x0 != null && radius != null) {
			g2d.drawOval(x0-radius, y0-radius, radius*2, radius*2);
		}
	}

}
