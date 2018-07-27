package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class for rotating direction of current state.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class RotateCommand implements Command {

	/** Angle at which state will be rotated */
	private double angle;
	
	/**
	 * Constructor for initializing object.
	 * 
	 * @param angle Provided angle.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Gets current state and rotates it's direction for 
	 * given angle.
	 * 
	 * @param ctx Context.
	 * @param painter Painter.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		state.getDirection().rotate(angle);
	}

}
