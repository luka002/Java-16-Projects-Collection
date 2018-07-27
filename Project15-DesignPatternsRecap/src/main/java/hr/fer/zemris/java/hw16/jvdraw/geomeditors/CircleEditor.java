package hr.fer.zemris.java.hw16.jvdraw.geomeditors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;

/**
 * Component that can edit provided Circle.
 * 
 * @author Luka Grgić
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 3445206372964288036L;
	/**
	 * Geometrical object that can be edited.
	 */
	private Circle circle;
	/**
	 * text field for editing center x coordinate
	 */
	private JTextField labelX0;
	/**
	 * text field for editing center y coordinate
	 */
	private JTextField labelY0;
	/**
	 * text field for editing circle radius
	 */
	private JTextField labelRadius;
	/**
	 * text field for editing circle color
	 */
	private JTextField labelColor;

	/**
	 * Constructor initializes text fields
	 * with values from circle.
	 * 
	 * @param circle geometrical object
	 */
	public CircleEditor(Circle circle) {
		labelX0 = new JTextField(Integer.toString(circle.getX0()));
		labelY0 = new JTextField(Integer.toString(circle.getY0()));
		labelRadius = new JTextField(Integer.toString(circle.getRadius()));
		labelColor = new JTextField(String.format("#%02X%02X%02X", 
				circle.getFgColor().getRed(), 
				circle.getFgColor().getGreen(), 
				circle.getFgColor().getBlue()
		).toString());
		
		this.circle = circle;
		initEditor();
	}

	/**
	 * Adds JTextFields to the component.
	 */
	private void initEditor() {
		setLayout(new GridLayout(0, 1));
		
		add(labelX0);
		add(labelY0);
		add(labelRadius);
		add(labelColor);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(labelX0.getText());
			Integer.parseInt(labelY0.getText());
			Integer.parseInt(labelRadius.getText());
			Color.decode(labelColor.getText());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Wrong parameters provided.");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setX0(Integer.parseInt(labelX0.getText()));
		circle.setY0(Integer.parseInt(labelY0.getText()));
		circle.setRadius(Integer.parseInt(labelRadius.getText()));
		circle.setFgColor(Color.decode(labelColor.getText()));
	}
}
