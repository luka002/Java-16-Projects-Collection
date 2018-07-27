package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Interface that represents listener that listens
 * when geometrical object has changed.
 *  
 * @author Luka Grgić
 */
public interface GeometricalObjectListener {

	/**
	 * Method that gets called when object has changed.
	 * 
	 * @param o geometrical object
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}