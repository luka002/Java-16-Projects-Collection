package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for representation of expression containing integer number.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	/** Expression's value */
	private int value;
	
	/**
	 * Constructor.
	 * 
	 * @param value Expression's value.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Method that returns expression as a string.
	 * 
	 * @return Value as string.
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
}
