package hr.fer.zemris.java.hw16.jvdraw.models;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Concrete DrawingModel. It stores all the geometrical objects.
 * It is subject and observer in the observer design pattern.
 * It is observer towards GeometricalObjects and it is subject towards
 * JDrawingCanvas and DrawingObjectListModel.
 * 
 * @author Luka Grgić
 */
public class MyDrawingModel implements DrawingModel, GeometricalObjectListener {
	
	/**
	 * Geometrical objects.
	 */
	private List<GeometricalObject> objects;
	/**
	 * Listeners.
	 */
	private List<DrawingModelListener> listeners;
	
	/**
	 * Constructor.
	 */
	public MyDrawingModel() {
		this.objects = new ArrayList<>();
		this.listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		int index = objects.indexOf(object);
		
		object.addGeometricalObjectListener(this);
		
		for (DrawingModelListener listener : listeners) {
			listener.objectsAdded(this, index, index);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		
		object.removeGeometricalObjectListener(this);
		
		for (DrawingModelListener listener : listeners) {
			listener.objectsRemoved(this, index, index);
		}
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		
		if (objects.size() <= 1 ) {
			offset = 0;
		} else if (index == 0 && offset == -1) {
			offset = 0;
		} else if (index == objects.size()-1 && offset == 1) {
			offset = 0;
		}
		
		objects.remove(object);
		objects.add(index + offset, object);
		
		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, index, index + offset);
		}
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		
		for (DrawingModelListener listener : listeners) {
			listener.objectsChanged(this, index, index);
		}
	}
	
	@Override
	public String toString() {
		BuildFileContent builder = new BuildFileContent();
		
		for (GeometricalObject object : objects) {
			object.accept(builder);
		}
		
		return builder.toString();
	}
	
	/**
	 * Visitor class that goes through objects
	 * and converts them to the string notation that
	 * is of .jvd format.
	 *  
	 * @author Luka Grgić
	 */
	private static class BuildFileContent implements GeometricalObjectVisitor {

		/**
		 * Builds objects as .jvd format.
		 */
		private StringBuilder builder = new StringBuilder();
		
		@Override
		public void visit(Line line) {
			builder.append("LINE ")
					.append(line.getX0() + " ")
					.append(line.getY0() + " ")
					.append(line.getX1() + " ")
					.append(line.getY1() + " ")
					.append(line.getFgColor().getRed() + " ")
					.append(line.getFgColor().getGreen() + " ")
					.append(line.getFgColor().getBlue() + "\n");
		}

		@Override
		public void visit(Circle circle) {
			builder.append("CIRCLE ")
					.append(circle.getX0() + " ")
					.append(circle.getY0() + " ")
					.append(circle.getRadius() + " ")
					.append(circle.getFgColor().getRed() + " ")
					.append(circle.getFgColor().getGreen() + " ")
					.append(circle.getFgColor().getBlue() + "\n");
		}

		@Override
		public void visit(FilledCircle filledCircle) {
			builder.append("FCIRCLE ")
					.append(filledCircle.getX0() + " ")
					.append(filledCircle.getY0() + " ")
					.append(filledCircle.getRadius() + " ")
					.append(filledCircle.getFgColor().getRed() + " ")
					.append(filledCircle.getFgColor().getGreen() + " ")
					.append(filledCircle.getFgColor().getBlue() + " ")
					.append(filledCircle.getBgColor().getRed() + " ")
					.append(filledCircle.getBgColor().getGreen() + " ")
					.append(filledCircle.getBgColor().getBlue() + "\n");
		}
		
		@Override
		public String toString() {
			return builder.toString();
		}
		
	}

}
