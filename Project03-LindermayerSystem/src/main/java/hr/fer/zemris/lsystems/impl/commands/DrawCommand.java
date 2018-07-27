package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Draws line for current state.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class DrawCommand implements Command {

	/** Step */
	private double step;
	
	/**
	 * Constructor for initializing object.
	 * 
	 * @param step Step.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	/**
	 * Gets current state and draws a line from current position
	 * in specified direction.
	 * 
	 * @param ctx Context.
	 * @param painter Painter.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		
		double x1 = state.getCurrentPosition().getX();
		double y1 = state.getCurrentPosition().getY();
		
		double x2 = x1 + state.getDirection().getX()*step*state.getLength();
		double y2 = y1 + state.getDirection().getY()*step*state.getLength();
		
		painter.drawLine(x1, y1, x2, y2, state.getColor(), 1);
		
		Vector2D newStart = new Vector2D(x2, y2);
		
		TurtleState newState = new TurtleState(newStart, 
												state.getDirection(), 
												state.getColor(), 
												state.getLength());
						
		ctx.popState();
		ctx.pushState(newState);
	}

}
