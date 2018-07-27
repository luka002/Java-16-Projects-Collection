package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Interface that represents observer in observer
 * design patters. It tells listeners when model has 
 * been changed.
 * 
 * @author Luka Grgić
 */
public interface DrawingModelListener {

	/**
	 * Method called when object has been added to the model.
	 * 
	 * @param source source
	 * @param index0 index0
	 * @param index1 index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
 
	/**
	 * Method called when object has been removed from the model.
	 * 
	 * @param source source
	 * @param index0 index0
	 * @param index1 index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
 
	/**
	 * Method called when object has been changed in the model.
	 * 
	 * @param source source
	 * @param index0 index0
	 * @param index1 index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}