package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for representation of expression containing string.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ElementString extends Element {

	/** Expression's value */
	private String value;
	
	/**
	 * Constructor.
	 * 
	 * @param value Expression's value.
	 */
	public ElementString(String value) {
		this.value= value;
	}

	/**
	 * Method that returns expression as a string.
	 * 
	 * @return Value as string.
	 */
	@Override
	public String asText() {
		return value;
	}
	
}
