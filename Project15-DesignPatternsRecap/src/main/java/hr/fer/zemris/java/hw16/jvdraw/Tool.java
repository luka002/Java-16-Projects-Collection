package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Interface that represents state in state design pattern.
 * 
 * @author Luka Grgić
 */
public interface Tool {
 
	
	/**
	 * Ignore.
	 * 
	 * @param e event
	 */
	public void mousePressed(MouseEvent e);
 
	/**
	 * Ignore.
	 * 
	 * @param e event
	 */
	public void mouseReleased(MouseEvent e);
 
	/**
	 * On first click starting coordinates calculated, 
	 * on second click object will be stored in the model.
	 * 
	 * @param e event
	 */
	public void mouseClicked(MouseEvent e);
 
	/**
	 * Calculates end coordinates for the drawing.
	 * 
	 * @param e event
	 */
	public void mouseMoved(MouseEvent e);
 
	/**
	 * Calculates end coordinates for the drawing.
	 * 
	 * @param e event
	 */
	public void mouseDragged(MouseEvent e);
 
	/**
	 * Draw current state on provided graphics.
	 * 
	 * @param g2d graphics
	 */
	public void paint(Graphics2D g2d);

}