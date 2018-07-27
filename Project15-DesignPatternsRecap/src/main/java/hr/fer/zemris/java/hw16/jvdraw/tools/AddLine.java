package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Class that implements Tool and represents concrete
 * state in state design pattern. This state is responsible
 * for drawing lines on the JDrawingCanvas. On first
 * mouse click beginning of the line will be drawn and
 * with second mouse click that line will be added to the
 * model. 
 * 
 * @author Luka Grgić
 */
public class AddLine implements Tool {

	/**
	 * Starting x coordinate.
	 */
	private Integer x0;
	/**
	 * Starting y coordinate.
	 */
	private Integer y0;
	/**
	 * Ending x coordinate.
	 */
	private Integer x1;
	/**
	 * Ending y coordinate.
	 */
	private Integer y1;
	/**
	 * Flag that tells if first click has been made.
	 */
	private boolean isClicked;
	/**
	 * Provides color for drawing the line.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Model that stores all geometrical objects.
	 */
	private DrawingModel model;
	
	/**
	 * Constructor.
	 * 
	 * @param fgColorProvider provides color for drawing the line
	 * @param model model that stores all geometrical objects.
	 */
	public AddLine(IColorProvider fgColorProvider, 	DrawingModel model) {
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
			int rgb = fgColorProvider.getCurrentColor().getRGB();
			
			model.add(new Line(x0, y0, x1, y1, new Color(rgb)));
			x0 = y0 = x1 = y1 = null;
			isClicked = false;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		calculateEnd(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		calculateEnd(e);
	}
	
	/**
	 * Calculates end x, y coordinates.
	 * @param e
	 */
	private void calculateEnd(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(fgColorProvider.getCurrentColor());
		
		if (x0 != null && x1 != null) {
			g2d.drawLine(x0, y0, x1, y1);
		}
	}

}
