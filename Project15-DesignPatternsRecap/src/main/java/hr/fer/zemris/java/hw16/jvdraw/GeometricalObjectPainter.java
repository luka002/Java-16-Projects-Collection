package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Concrete visitor that draws all the geometrical objects
 * on the provided graphics.
 * 
 * @author Luka Grgić.
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	
	/**
	 * Graphics to draw objects on
	 */
	private Graphics2D g2d;

	/**
	 * Constructor.
	 * 
	 * @param g2d graphics to draw objects on
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getFgColor());
		
		g2d.drawLine(
				line.getX0(), 
				line.getY0(), 
				line.getX1(), 
				line.getY1()
		);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getFgColor());
		
		g2d.drawOval(
				circle.getX0()-circle.getRadius(), 
				circle.getY0()-circle.getRadius(), 
				circle.getRadius()*2,
				circle.getRadius()*2
		);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int centerX = filledCircle.getX0()-filledCircle.getRadius();
		int centerY = filledCircle.getY0()-filledCircle.getRadius();
		
		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(centerX, centerY, filledCircle.getRadius()*2, filledCircle.getRadius()*2);
		
		g2d.setColor(filledCircle.getFgColor());
		g2d.drawOval(centerX, centerY, filledCircle.getRadius()*2, filledCircle.getRadius()*2);
	}

}
