package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Scales line for current state.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ScaleCommand implements Command {
	
	/** Factor of scaling */
	private double factor;
	
	/**
	 * Constructor for initializing object.
	 * 
	 * @param factor Factor of scaling.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Gets current state and scales a length od line for 
	 * drawing.
	 * 
	 * @param ctx Context.
	 * @param painter Painter.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		double length = state.getLength();
		
		state.setLength(length*factor);
	}

}
