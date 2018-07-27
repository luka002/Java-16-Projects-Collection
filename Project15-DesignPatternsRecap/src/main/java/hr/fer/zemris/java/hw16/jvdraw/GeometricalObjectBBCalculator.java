package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Concrete visitor that calculate the bounding box of
 * all objects.
 * 
 * @author Luka Grgić.
 */
class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Top left x coordinate.
	 */
	private int x0;
	/**
	 * Top left y coordinate.
	 */
	private int y0;
	/**
	 * Bottom right x coordinate.
	 */
	private int x1;
	/**
	 * Bottom right y coordinate.
	 */
	private int y1;
	/**
	 * Is it first pass
	 */
	private boolean firstPass = true;

	@Override
	public void visit(Line line) {
		int boxX0 = Math.min(line.getX0(), line.getX1());
		int boxY0 = Math.min(line.getY0(), line.getY1());
		int boxX1 = Math.max(line.getX0(), line.getX1());
		int boxY1 = Math.max(line.getY0(), line.getY1());
		
		if (firstPass) {
			setBox(boxX0, boxY0, boxX1, boxY1);
		} else {
			updateBox(boxX0, boxY0, boxX1, boxY1);
		}
	}		

	@Override
	public void visit(Circle circle) {
		int boxX0 = circle.getX0()-circle.getRadius();
		int boxY0 = circle.getY0()-circle.getRadius();
		int boxX1 = circle.getX0()+circle.getRadius();
		int boxY1 = circle.getY0()+circle.getRadius();
		
		if (firstPass) {
			setBox(boxX0, boxY0, boxX1, boxY1);
		} else {
			updateBox(boxX0, boxY0, boxX1, boxY1);
		}
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int boxX0 = filledCircle.getX0()-filledCircle.getRadius();
		int boxY0 = filledCircle.getY0()-filledCircle.getRadius();
		int boxX1 = filledCircle.getX0()+filledCircle.getRadius();
		int boxY1 = filledCircle.getY0()+filledCircle.getRadius();
		
		if (firstPass) {
			setBox(boxX0, boxY0, boxX1, boxY1);
		} else {
			updateBox(boxX0, boxY0, boxX1, boxY1);
		}
	}
	
	/**
	 * Sets initial bounding box.
	 * 
	 * @param boxX0 Top left x coordinate
	 * @param boxY0 Top left y coordinate
	 * @param boxX1 Bottom right x coordinate
	 * @param boxY1 Bottom right y coordinate
	 */
	private void setBox(int boxX0, int boxY0, int boxX1, int boxY1) {
		x0 = boxX0;
		y0 = boxY0;
		x1 = boxX1;
		y1 = boxY1;
		firstPass = false;
	}
	
	/**
	 * Updates bounding box.
	 * 
	 * @param boxX0 Top left x coordinate
	 * @param boxY0 Top left y coordinate
	 * @param boxX1 Bottom right x coordinate
	 * @param boxY1 Bottom right y coordinate
	 */
	private void updateBox(int boxX0, int boxY0, int boxX1, int boxY1) {
		x0 = Math.min(x0, boxX0);
		y0 = Math.min(y0, boxY0);
		x1 = Math.max(x1, boxX1);
		y1 = Math.max(y1, boxY1);
	}
	
	/**
	 * Gets bounding box
	 * 
	 * @return bounding bod as Rectangle
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(x0, y0, x1-x0, y1-y0);
	}
	
}