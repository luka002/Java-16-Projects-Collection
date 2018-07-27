package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geomeditors.LineEditor;

/**
 * Concrete GeometricalObject object that represents a line.
 * 
 * @author Luka Grgić
 */
public class Line extends GeometricalObject {

	/**
	 * Starting x coordinate.
	 */
	private int x0;
	/**
	 * Starting y coordinate.
	 */
	private int y0; 
	/**
	 * Ending x coordinate.
	 */
	private int x1; 
	/**
	 * Ending y coordinate.
	 */
	private int y1;
	/**
	 * Line color.
	 */
	private Color fgColor;
	/**
	 * Listeners for change.
	 */
	private List<GeometricalObjectListener> listeners;
	
	/**
	 * Constructor.
	 * 
	 * @param x0 Starting x coordinate.
	 * @param y0 Starting y coordinate.
	 * @param x1 Ending x coordinate.
	 * @param y1 Ending y coordinate.
	 * @param fgColor Line color.
	 */
	public Line(int x0, int y0, int x1, int y1, Color fgColor) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.fgColor = fgColor;
		this.listeners = new ArrayList<>();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Notifies all listeners that object property has changed.
	 */
	private void objectChanged() {
		for (GeometricalObjectListener listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}

	/**
	 * Get starting x coordinate.
	 * 
	 * @return starting x coordinate
	 */
	public int getX0() {
		return x0;
	}

	/**
	 * Set starting x coordinate.
	 * 
	 * @param x0 starting x coordinate
	 */
	public void setX0(int x0) {
		this.x0 = x0;
		objectChanged();
	}

	/**
	 * Get starting y coordinate.
	 * 
	 * @return starting y coordinate
	 */
	public int getY0() {
		return y0;
	}

	/**
	 * Set starting y coordinate.
	 * 
	 * @param y0 starting y coordinate
	 */
	public void setY0(int y0) {
		this.y0 = y0;
		objectChanged();
	}

	/**
	 * Get ending x coordinate.
	 * 
	 * @return ending x coordinate
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Set ending x coordinate
	 * 
	 * @param x1 ending x coordinate
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		objectChanged();
	}

	/**
	 * Get ending y coordinate.
	 * 
	 * @return ending y coordinate
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Set ending y coordinate.
	 * 
	 * @param y1 ending y coordinate
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		objectChanged();
	}

	/**
	 * Get foreground color.
	 * 
	 * @return foreground color
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Set foreground color.
	 * 
	 * @param fgColor foreground color
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		objectChanged();
	}
	
	@Override
	public String toString() {
		return "Line (" + x0 + "," + y0 + ")-(" + x1 + "," + y1 + ")";
	}
	
}
