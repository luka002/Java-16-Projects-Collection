package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node representing a single for-loop construct.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ForLoopNode extends Node {

	/** Initial variable */
	private ElementVariable variable;
	/** Expression start */
	private Element startExpression;
	/** Expression end */
	private Element endExpression;
	/** Expression step */
	private Element stepExpression;
	
	/**
	 * Constructor with all parameters passed.
	 * 
	 * @param variable Initial variable .
	 * @param start Expression Expression start.
	 * @param end ExpressionExpression end.
	 * @param step Expression Expression step.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Constructor with 3 parameters passed.
	 * 
	 * @param variable Initial variable .
	 * @param start Expression Expression start.
	 * @param end ExpressionExpression end.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}
	
	/**
	 * @return Returns data as it was originally created.
	 */
	@Override
	public String getText() {
		return "{$ FOR " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() +
				" " + (stepExpression==null ? "" : (stepExpression.asText() + " ")) + "$}";
	}
	
}
