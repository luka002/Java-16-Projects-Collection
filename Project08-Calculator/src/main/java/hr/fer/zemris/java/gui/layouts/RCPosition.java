package hr.fer.zemris.java.gui.layouts;

/**
 * Defined position of component in CalcLayout.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class RCPosition {

	/** Position row */
	private final int row;
	/** Position column */
	private final int column;
	
	/**
	 * Constructor.
	 * 
	 * @param row row
	 * @param column column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}
