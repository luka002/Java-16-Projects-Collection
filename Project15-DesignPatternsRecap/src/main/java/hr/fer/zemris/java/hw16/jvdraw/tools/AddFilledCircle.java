package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;

/**
 * Class that implements Tool and represents concrete
 * state in state design pattern. This state is responsible
 * for drawing filled circle on the JDrawingCanvas. On first
 * mouse click center of the circle calculated and
 * with second mouse click that filled circle will be added to the
 * model. 
 * 
 * @author Luka Grgić
 */
public class AddFilledCircle implements Tool {

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
	 * Provides color for drawing the circle outline.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Provides color for filling the circle.
	 */
	private IColorProvider bgColorProvider;
	/**
	 * Model that stores all geometrical objects.
	 */
	private DrawingModel model;
	
	/**
	 * Constructor.
	 * 
	 * @param fgColorProvider provides color for drawing the circle.
	 * @param bgColorProvider provides color for filling the circle.
	 * @param model model that stores all geometrical objects.
	 */
	public AddFilledCircle(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel model) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
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
			model.add(new FilledCircle(x0, y0, radius, fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor()));
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
		if (x0 != null && radius != null) {
			int trueX0 = x0-radius;
			int trueY0 = y0-radius;
			
			g2d.setColor(bgColorProvider.getCurrentColor());
			g2d.fillOval(trueX0, trueY0, radius*2, radius*2);
			
			g2d.setColor(fgColorProvider.getCurrentColor());
			g2d.drawOval(trueX0, trueY0, radius*2, radius*2);
		}
	}

}
