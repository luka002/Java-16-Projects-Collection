package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public class Node {
	
	/** Array containing child nodes. */
	private ArrayIndexedCollection array;

	/**
	 * Method for adding child node.
	 * 
	 * @param child Node to be added.
	 */
	public void addChildNode(Node child) {
		if (array == null) {
			array = new ArrayIndexedCollection();
		}
		
		array.add(child);
	}
	
	/**
	 * Method that tells you number of children.
	 * 
	 * @return Number of children.
	 */
	public int numberOfChildren() {
		return array.size();
	}
	
	/**
	 * Method that finds child at specified index.
	 * 
	 * @param index Index of child.
	 * @return Child at specified index.
	 */
	public Node getChild(int index) {
		return (Node)array.get(index);	
	}
	
	/**
	 * Method for converting node to string.
	 * Here it always returns empty string.
	 * 
	 * @return Empty string.
	 */
	public String getText() {
		return "";
	}
	
}
