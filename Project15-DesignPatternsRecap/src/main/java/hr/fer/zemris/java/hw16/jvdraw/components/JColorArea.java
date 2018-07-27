package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

/**
 * Component that allows use to choose color with
 * which he will draw on JDrawingCanvas. When component
 * is pressed JColorChooser dialog is opened so user can
 * select color. This class is also Subject in observer 
 * design pattern and it tells his listeners when color 
 * has changed.
 * 
 * @author Luka Grgić
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = -8179920274416062578L;
	/**
	 * Currently selected color.
	 */
	private Color selectedColor;
	/**
	 * Listeners that listen when color have been changed.
	 */
	private List<ColorChangeListener> listeners;
	
	/**
	 * Constructor.
	 * 
	 * @param selectedColor initial color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.listeners = new ArrayList<>();
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeColor();	
			}
		});
	}
	
	/**
	 * Allows user to change color and
	 * notifies its listeners.
	 */
	private void changeColor() {
		Color newColor = JColorChooser.showDialog(
                JColorArea.this,
                "Choose Color",
                selectedColor);
		
		if (newColor != null) {
			notifyListeners(selectedColor, newColor);
			selectedColor = newColor;
			repaint();
		}
	}
	
	/**
	 * Notifies listeners that color has changed.
	 * 
	 * @param oldColor old color
	 * @param newColor new color
	 */
	private void notifyListeners(Color oldColor, Color newColor) {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, newColor);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
}
