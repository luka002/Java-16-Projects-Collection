package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that stores current state of turtle.
 * 
 * @author Luka Grgić
 * @version 1.0 
 */
public class TurtleState {

	/** Current turtle position */
	Vector2D currentPosition;
	/** Direction that turtle is turned to as normalized vector */
	Vector2D direction;
	/** Turtle drawing color */
	Color color;
	/** Length of turtle move */
	double length;
	
	/**
	 * Constructor for initializing object.
	 * 
	 * @param current Position Current turtle position
	 * @param direction Direction that turtle is turned to as normalized vector
	 * @param color Turtle drawing color
	 * @param length Length of turtle move
	 */
	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double length) {
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.length = length;
	}
	
	/**
	 * Copies current state.
	 * 
	 * @return TurtleState with copied values.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), color, length);
	}

	/**
	 * @return Returns current position.
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * @return Returns direction vector.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * @return Returns drawing color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param color Drawing color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return Returns current length.
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * @param length Sets length.
	 */
	public void setLength(double length) {
		this.length = length;
	}
	
}
