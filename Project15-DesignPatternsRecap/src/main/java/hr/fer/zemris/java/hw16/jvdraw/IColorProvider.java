package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface representing subject in observer design pattern.
 * It tells listeners when color has been changed.
 * 
 * @author Luka Grgić
 */
public interface IColorProvider {
 
	/**
	 * Get current color.
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();
 
	/**
	 * Add listener.
	 * 
	 * @param l listener
	 */
	public void addColorChangeListener(ColorChangeListener l);
 
	/**
	 * Remove listener.
	 * 
	 * @param l listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);
	
}