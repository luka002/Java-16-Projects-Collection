package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class for removing state from stack.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public class PopCommand implements Command {

	/**
	 * Removes state from top os the stack from 
	 * provided context.
	 * 
	 * @param ctx Context
	 * @param painter Painter.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
