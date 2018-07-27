package hr.fer.zemris.java.hw16.jvdraw;

/**
 * Abstract class that represents a geometrical object.
 * It is also subject in the observer design pattern and
 * listeners can be added and removed. It also accepts
 * GeometricalObjectVisitor that represents visitor design pattern.
 * 
 * @author Kim Jong Un
 *
 */
public abstract class GeometricalObject {

	/**
	 * Accepts GeometricalObjectVisitor that represents visitor design pattern.
	 * 
	 * @param v GeometricalObjectVisitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Created object that is responsible for editing this object.
	 * 
	 * @return GeometricalObjectEditor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Add listener.
	 * 
	 * @param l listener.
	 */
	public abstract void addGeometricalObjectListener(GeometricalObjectListener l);
	
	/**
	 * Remove listener.
	 * 
	 * @param l listener.
	 */
	public abstract void removeGeometricalObjectListener(GeometricalObjectListener l);
	
}
