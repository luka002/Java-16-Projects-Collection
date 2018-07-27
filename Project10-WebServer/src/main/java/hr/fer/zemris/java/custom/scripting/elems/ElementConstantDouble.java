package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for representation of expression containing double number.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ElementConstantDouble extends Element {

	/** Expression's value */
	private double value;
	
	/**
	 * Constructor.
	 * 
	 * @param value Expression's value.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Method that returns expression as a string.
	 * 
	 * @return Value as string.
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
}
