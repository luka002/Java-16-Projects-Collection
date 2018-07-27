package hr.fer.zemris.java.gui.charts;

/**
 * Holds x and y values.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class XYValue {

	/** x-value */
	private final int x;
	/** y-value */
	private final int y;
	
	/**
	 * Constructor.
	 * @param x x-value.
	 * @param y y-value.
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x-value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y-value.
	 */
	public int getY() {
		return y;
	}

}
