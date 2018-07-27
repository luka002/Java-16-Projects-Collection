package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that is some type of command.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 */
public interface Command {

	/**
	 * Method that executes something.
	 * 
	 * @param ctx Context.
	 * @param painter Painter.
	 */
	void execute(Context ctx, Painter painter);
	
}
