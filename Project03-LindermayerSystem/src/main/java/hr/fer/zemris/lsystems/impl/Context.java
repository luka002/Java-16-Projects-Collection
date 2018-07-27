package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that behaves as a stack for TurtleState objects.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public class Context {

	/** Stack */
	ObjectStack stack;

	/**
	 * Constructor initializes stack.
	 */
	public Context() {
		this.stack = new ObjectStack();
	}
	/**
	 * @return Returns state from top of the stack
	 * without removing it.
	 */
	public TurtleState getCurrentState() {
		return (TurtleState)stack.peek();
	}
	
	/**
	 * Pushes given state on top of the stack.
	 * 
	 * @param state State to be pushed.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Removes state from top of the stack.
	 */
	public void popState() {
		stack.pop();
	}
	
}
