package hr.fer.zemris.java.hw16.jvdraw.geomeditors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;

/**
 * Component that can edit provided FilledCircle.
 * 
 * @author Luka Grgić
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = -8148461450199139579L;
	/**
	 * Geometrical object that can be edited.
	 */
	private FilledCircle filledCircle;
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
	 * text field for editing circle outline color
	 */
	private JTextField labelFgColor;
	/**
	 * text field for editing circle background color
	 */
	private JTextField labelBgColor;

	/**
	 * Constructor initializes text fields
	 * with values from filledCircle.
	 * 
	 * @param filledCircle geometrical object
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		labelX0 = new JTextField(Integer.toString(filledCircle.getX0()));
		labelY0 = new JTextField(Integer.toString(filledCircle.getY0()));
		labelRadius = new JTextField(Integer.toString(filledCircle.getRadius()));
		labelFgColor = new JTextField(String.format("#%02X%02X%02X", 
				filledCircle.getFgColor().getRed(), 
				filledCircle.getFgColor().getGreen(), 
				filledCircle.getFgColor().getBlue()
		).toString());
		labelBgColor = new JTextField(String.format("#%02X%02X%02X", 
				filledCircle.getBgColor().getRed(), 
				filledCircle.getBgColor().getGreen(), 
				filledCircle.getBgColor().getBlue()
		).toString());
		
		this.filledCircle = filledCircle;
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
		add(labelFgColor);
		add(labelBgColor);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(labelX0.getText());
			Integer.parseInt(labelY0.getText());
			Integer.parseInt(labelRadius.getText());
			Color.decode(labelFgColor.getText());
			Color.decode(labelBgColor.getText());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Wrong parameters provided.");
		}
	}

	@Override
	public void acceptEditing() {
		filledCircle.setX0(Integer.parseInt(labelX0.getText()));
		filledCircle.setY0(Integer.parseInt(labelY0.getText()));
		filledCircle.setRadius(Integer.parseInt(labelRadius.getText()));
		filledCircle.setFgColor(Color.decode(labelFgColor.getText()));
		filledCircle.setBgColor(Color.decode(labelBgColor.getText()));
	}

}
