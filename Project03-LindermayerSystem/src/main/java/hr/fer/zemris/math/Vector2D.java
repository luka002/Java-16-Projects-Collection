package hr.fer.zemris.math;

/**
 * Class that represents vector in two dimensional field.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Vector2D {
	
	/** x coordinate of vector */
	private double x;
	/** y coordinate of vector */
	private double y;
	
	/**
	 * Constructor for initializing x and y coordinates.
	 * 
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return Returns x coordinate.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return Returns y coordinate.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates this vector for given offset.
	 * 
	 * @param offset Value for which this vector will
	 * be translated for.
	 */
	public void translate(Vector2D offset) {
		x += offset.getX();
		y += offset.getY();
	}
	
	/**
	 * Returns this vector translated for offset without
	 * changing this vector.
	 * 
	 * @param offset Value of translation.
	 * @return New vector corresponding to this one translated
	 * for offset.
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}
	
	/**
	 * Rotates this vector for specified angle. Angle is provided
	 * in degrees.
	 * 
	 * @param angle Angle for which vector will be rotated in degrees.
	 */
	public void rotate(double angle) {
		double angleRad = (angle*Math.PI)/180;
		
		double x1 = x * Math.cos(angleRad) - y * Math.sin(angleRad);
	    double y1 = x * Math.sin(angleRad) + y * Math.cos(angleRad);
	    
	    x = x1;
	    y = y1;
	}
	
	/**
	 * Returns this vector rotated for specified angle without changing
	 * this vector. Angle is provided in degrees.
	 * 
	 * @param angle Angle for which vector will be rotated in degrees.
	 * @return New vector corresponding to this one rotated
	 * for given angle.
	 */
	public Vector2D rotated(double angle) {
		double angleRad = (angle*Math.PI)/180;
		
		return new Vector2D(x * Math.cos(angleRad) - y * Math.sin(angleRad),
	    					x * Math.sin(angleRad) + y * Math.cos(angleRad));
	}
	
	/**
	 * Each coordinate gets scaled based on given
	 * scaler coefficient.
	 * 
	 * @param scaler Coefficient for which every
	 * coordinate will be multiplied with.
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns this vector scaled for specified coefficient without
	 * changing this vector.
	 * 
	 * @param scaler  Coefficient for which every
	 * coordinate will be multiplied with.
	 * @return New vector corresponding to this one scaled
	 * for given coefficient.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x*scaler, y*scaler);
	}
	
	/**
	 * Copies this vector. 
	 * 
	 * @return Returns new vector with same dimensions as this one.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
}
