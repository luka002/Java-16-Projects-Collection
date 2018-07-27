package hr.fer.zemris.java.hw16.jvdraw.geomeditors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Component that can edit provided Line.
 * 
 * @author Luka Grgić
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 6255223931993361595L;
	/**
	 * Geometrical object that can be edited.
	 */
	private Line line;
	/**
	 * text field for editing starting x coordinate
	 */
	private JTextField labelX0;
	/**
	 * text field for editing starting y coordinate
	 */
	private JTextField labelY0;
	/**
	 * text field for editing ending x coordinate
	 */
	private JTextField labelX1;
	/**
	 * text field for editing ending y coordinate
	 */
	private JTextField labelY1;
	/**
	 * text field for editing line color
	 */
	private JTextField labelColor;

	/**
	 * Constructor initializes text fields
	 * with values from line.
	 * 
	 * @param line geometrical object
	 */
	public LineEditor(Line line) {
		labelX0 = new JTextField(Integer.toString(line.getX0()));
		labelY0 = new JTextField(Integer.toString(line.getY0()));
		labelX1 = new JTextField(Integer.toString(line.getX1()));
		labelY1 = new JTextField(Integer.toString(line.getY1()));
		labelColor = new JTextField(String.format("#%02X%02X%02X", 
				line.getFgColor().getRed(), 
				line.getFgColor().getGreen(), 
				line.getFgColor().getBlue()
		).toString());
		
		this.line = line;
		initEditor();
	}

	/**
	 * Adds JTextFields to the component.
	 */
	private void initEditor() {
		setLayout(new GridLayout(0, 1));
		
		add(labelX0);
		add(labelY0);
		add(labelX1);
		add(labelY1);
		add(labelColor);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(labelX0.getText());
			Integer.parseInt(labelY0.getText());
			Integer.parseInt(labelX1.getText());
			Integer.parseInt(labelY1.getText());
			Color.decode(labelColor.getText());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Wrong parameters provided.");
		}
	}

	@Override
	public void acceptEditing() {
		line.setX0(Integer.parseInt(labelX0.getText()));
		line.setY0(Integer.parseInt(labelY0.getText()));
		line.setX1(Integer.parseInt(labelX1.getText()));
		line.setY1(Integer.parseInt(labelY1.getText()));
		line.setFgColor(Color.decode(labelColor.getText()));
	}

}
