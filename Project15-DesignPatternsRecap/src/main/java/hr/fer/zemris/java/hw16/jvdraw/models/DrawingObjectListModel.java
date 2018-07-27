package hr.fer.zemris.java.hw16.jvdraw.models;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;

/**
 * Class representing list model. Elements from the list
 * are acquired from the DrawingModel(Adapter design pattern).
 * It is also listener in the observer design pattern. It listens
 * to changes in the DrawingModel.
 * 
 * @author Luka Grgić
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 6078416793607036878L;
	/**
	 * Drawing model.
	 */
	private DrawingModel model;

	/**
	 * Constructor.
	 * 
	 * @param model DrawingModel
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
		
	}

}
