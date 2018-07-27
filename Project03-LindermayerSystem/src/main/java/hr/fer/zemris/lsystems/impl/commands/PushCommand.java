package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class for pushing state on stack.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public class PushCommand implements Command {

	/**
	 * Copies state from top of the ctx stack 
	 * and pushes it on the top of the stack.
	 * 
	 * @param ctx context.
	 * @param painer Painter.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState().copy();
		ctx.pushState(state);
	}

}
