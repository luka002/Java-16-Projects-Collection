package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface that observer will implement to listen
 * to color change.
 * 
 * @author Luka Grgić
 *
 */
public interface ColorChangeListener {

	/**
	 * Method that gets called when color has changed.
	 * 
	 * @param source source of change
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}