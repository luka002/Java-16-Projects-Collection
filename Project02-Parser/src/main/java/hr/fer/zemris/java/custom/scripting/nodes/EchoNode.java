package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Node representing a command which generates some textual output dynamically.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class EchoNode extends Node {
	
	/** Array of nodes elements */
	private Element[] elements;

	/**
	 * Constructor that accepts data.
	 * 
	 * @param elements Array of elements.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * @return Returns array of elements.
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * @return Returns data as it was originaly created.
	 */
	@Override
	public String getText() {
		StringBuilder text = new StringBuilder();
		
		text.append("{$");
		
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] instanceof ElementString) {
				StringBuilder checkForEscape = new StringBuilder(elements[i].asText());
				
				for (int j = 0; j < checkForEscape.length(); j++ ) {
					if (checkForEscape.charAt(j) == '\\' || checkForEscape.charAt(j) == '"') {
						checkForEscape.insert(j, '\\');
						j++;
					}
				}
				
				text.append("\"" + checkForEscape.toString() + "\"" + " ");
			} else if (elements[i] instanceof ElementFunction) {
				text.append("@" + elements[i].asText() + " ");
			} else {
				text.append(elements[i].asText() + " ");	
			}
		}
		
		text.append("$}");
		
		return text.toString();
	}
	
}
