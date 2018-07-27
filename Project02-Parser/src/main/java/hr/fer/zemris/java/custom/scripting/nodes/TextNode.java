package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing piece of textual data.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class TextNode extends Node {

	/** Holder for data */
	private String text;

	/**
	 * Constructor that accepts data.
	 * 
	 * @param text Textual data.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * @return Returns data as it was originally written.
	 */
	@Override
	public String getText() {
		StringBuilder checkForEscape = new StringBuilder(text);
		
		for (int j = 0; j < checkForEscape.length(); j++ ) {
			if (checkForEscape.charAt(j) == '\\' || checkForEscape.charAt(j) == '{') {
				checkForEscape.insert(j, '\\');
				j++;
			}
		}
		
		return checkForEscape.toString();
	}
	
}
