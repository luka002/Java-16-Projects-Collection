package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for representation of expression containing operator.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ElementOperator extends Element {

	/** Expression's value */
	private String symbol;

	/**
	 * Constructor.
	 * 
	 * @param value Expression's value.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Method that returns expression as a string.
	 * 
	 * @return Symbol as string.
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
}
