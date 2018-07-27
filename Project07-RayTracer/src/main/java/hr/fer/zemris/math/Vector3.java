package hr.fer.zemris.math;

/**
 * Representation of 3D vector.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Vector3 {
	
	/** Vector x-component */
	private double x;
	/** Vector y-component */
	private double y;
	/** Vector z-component */
	private double z;
	
	/**
	 * Constructor.
	 *
	 * @param x x-component
	 * @param y y-component
	 * @param z z-component
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	} 
	
	/**
	 * Calculates norm of point.
	 * 
	 * @return value of norm.
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	} 
	
	/**
	 * Transforms current vector to normalized one.
	 * 
	 * @return Normalized vector.
	 */
	public Vector3 normalized() {
		double length = norm();
		return new Vector3(x/length, y/length, z/length);
	} 
	
	/**
	 * Translates vector by given vector.
	 * 
	 * @param other Translation vector
	 * @return translated vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x+other.getX(), y+other.getY(), z+other.getZ());
	}
	
	/**
	 * Translates this vector by negative provided one.
	 * 
	 * @param other translation vector
	 * @return translated vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x-other.getX(), y-other.getY(), z-other.getZ());
	}
	
	/**
	 * Calculates scalar product this*other
	 * 
	 * @param other other vector
	 * @return scalar product
	 */
	public double dot(Vector3 other) {
		return x*other.getX() + y*other.getY() + z*other.getZ();
	}
	
	/**
	 * Returns vector that is calculated as <i>vector-product</i> this * other.
	 * 
	 * @param other other vector
	 * @return vector product.
	 */
	public Vector3 cross(Vector3 other) {
		double newX = y*other.getZ() - z*other.getY();
		double newY = z*other.getX() - x*other.getZ();
		double newZ = x*other.getY() - y*other.getX();
		
		return new Vector3(newX, newY, newZ);
	}
	
	/**
	 * Returns new vector that is equal to this multiplied by
	 * given scalar value.
	 * 
	 * @param s scalar value
	 * @return new multiplied point
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
	}
	
	/**
	 * Calculates cos between this and other vector.
	 * 
	 * @param other other vector.
	 * @return cos
	 */
	public double cosAngle(Vector3 other) {
		return Math.cos(Math.acos(dot(other)/(norm()*other.norm())));
	} 
	
	/**
	 * @return x-component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return y-component
	 */
	public double getY() {
		return y;
	} 
	
	/**
	 * @return z-component
	 */
	public double getZ() {
		return z;
	} 
	
	/**
	 * Transforms vector to array with vectors components.
	 * 
	 * @return array
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	} 
	
	/**
	 * Transforms vector into string representation.
	 * 
	 * @returns string representation.
	 */
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
