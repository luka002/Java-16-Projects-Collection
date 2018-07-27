package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Used for representation of expression containing variable.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ElementVariable extends Element {

	/** Expression's value */
	private String name;
	
	/**
	 * Constructor.
	 * 
	 * @param value Expression's representation.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Method that returns expression as a string.
	 * 
	 * @return Name as string.
	 */
	@Override
	public String asText() {
		return name;
	}
	
}
