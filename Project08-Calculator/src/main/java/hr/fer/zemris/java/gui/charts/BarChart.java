package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Holds data for BarChartComponent.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class BarChart {

	/** x-y chart values*/
	private List<XYValue> values;
	/** x-description */
	private String xDescription;
	/** y-description */
	private String yDescription;
	/** y-min */
	private int yMin;
	/** y-max */
	private int yMax;
	/** y-step */
	private int yDistance;
	
	/**
	 * Constructor.
	 * @param values x-y values
	 * @param xDescription x-decsription
	 * @param yDescription y-decsription
	 * @param yMin y-min
	 * @param yMax y-max
	 * @param yDistance y-step
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int yDistance) {
		super();
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDistance = yDistance;
	}

	/**
	 * @return x-y values.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return x-description.
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * @return y-description.
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * @return y-min.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return y-max.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return y-step.
	 */
	public int getyDistance() {
		return yDistance;
	}
	
}
