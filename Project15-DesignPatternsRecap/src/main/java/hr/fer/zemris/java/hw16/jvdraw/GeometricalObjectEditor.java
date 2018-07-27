package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.JPanel;

/**
 * Component that allows editing of geometrical objects.
 * 
 * @author Luka Grgić
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * Auto Generated
	 */
	private static final long serialVersionUID = -8761367360364499342L;
	/**
	 * Checks if there are any errors after editing.
	 */
	public abstract void checkEditing();
	/**
	 * Saves changes.
	 */
	public abstract void acceptEditing();

}