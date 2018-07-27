package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Provides color for current state.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ColorCommand implements Command {
	 
	/** Color for drawing */
	Color color; 

	/**
	 * Constructor initializes object.
	 * 
	 * @param color Color for drawing.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	/**
	 * Sets color for ctx state.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		state.setColor(color);
	}

}
