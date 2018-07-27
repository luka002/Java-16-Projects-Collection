package hr.fer.zemris.java.gui.calc;

/**
 * Calculator listener.
 * 
 * @author Luka Grgić.
 * @version 1.0
 */
public interface CalcValueListener {
	
	/**
	 * Method called when value in calculator changes.
	 * 
	 * @param model model
	 */
	void valueChanged(CalcModel model);
	
}