package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Interface that represents visitor design patter.
 * 
 * @author Luka Grgić
 */
public interface GeometricalObjectVisitor {

	/**
	 * Called when Line is visited.
	 * 
	 * @param line line
	 */
	public abstract void visit(Line line);
 
	/**
	 * Called when Circle is visited.
	 * 
	 * @param circle circle
	 */
	public abstract void visit(Circle circle);
 
	/**
	 * Called when FilledCircle is visited
	 * 
	 * @param filledCircle filled circle
	 */
	public abstract void visit(FilledCircle filledCircle);

}