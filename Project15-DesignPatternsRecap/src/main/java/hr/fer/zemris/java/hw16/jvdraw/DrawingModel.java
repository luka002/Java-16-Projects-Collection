package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Interface that represents a model that
 * stores all geometrical objects and those are
 * the objects that will be drawn on the JDrawingCanvas.
 * It is also a subject in the observer design pattern.
 * 
 * @author Luka Grgić
 */
public interface DrawingModel {
	
	/**
	 * Gets number of objects stored in the model.
	 * 
	 * @return number of objects stored in the model.
	 */
	public int getSize();
	
	/**
	 * Gets object at specified index.
	 * 
	 * @param index index from which object will be fetched
	 * @return object at specified index
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Adds object to the model.
	 * 
	 * @param object geometrical object
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Adds listener.
	 * 
	 * @param l listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes listener.
	 * 
	 * @param l listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes object from the model.
	 * 
	 * @param object geometrical object
	 */
	void remove(GeometricalObject object);
	
	/**
	 * Changes object index in the model.
	 * 
	 * @param object geometrical object
	 * @param offset offset from current position
	 */
	void changeOrder(GeometricalObject object, int offset);
	
}
