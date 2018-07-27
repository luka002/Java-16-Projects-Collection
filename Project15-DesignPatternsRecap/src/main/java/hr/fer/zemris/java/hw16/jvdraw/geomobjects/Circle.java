package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geomeditors.CircleEditor;

/**
 * Concrete GeometricalObject object that represents a circle.
 * 
 * @author Luka Grgić
 */
public class Circle extends GeometricalObject {
	
	/**
	 * Center x coordinate.
	 */
	private int x0;
	/**
	 * Center y coordinate.
	 */
	private int y0;
	/**
	 * Radius.
	 */
	private int radius;
	/**
	 * Circle color.
	 */
	private Color fgColor;
	/**
	 * Listeners for change.
	 */
	private List<GeometricalObjectListener> listeners;
	
	/**
	 * Constructor. 
	 * 
	 * @param x0 Center x coordinate.
	 * @param y0 Center y coordinate.
	 * @param radius Radius.
	 * @param fgColor Circle color.
	 */
	public Circle(int x0, int y0, int radius, Color fgColor) {
		this.x0 = x0;
		this.y0 = y0;
		this.radius = radius;
		this.fgColor = fgColor;
		listeners = new ArrayList<>();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
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
	 * Get radius.
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Set radius.
	 * 
	 * @param radius radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
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
		return "Circle (" + x0 + "," + y0 + "), " + radius;
	}
	
}
