package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddCircle;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddFilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddLine;

/**
 * Component that is responsible for drawing objects
 * from the model. It is also observer from observer
 * design pattern. It listens to changes in the model and
 * repaint when they occur. 
 * 
 * @author Luka Grgić
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Auto generated.
	 */
	private static final long serialVersionUID = 6890032414502248058L;
	/**
	 * Model.
	 */
	private DrawingModel model;
	/**
	 * State.
	 */
	private Tool state;
	/**
	 * Visitor that draws objects.
	 */
	private GeometricalObjectPainter painter;
	/**
	 * Button group.
	 */
	private ButtonGroup group;
	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColor;
	/**
	 * Background color provider.
	 */
	private IColorProvider bgColor;
	/**
	 * Mouse adapter.
	 */
	private final MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			state.mouseClicked(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			state.mouseDragged(e);
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			state.mouseMoved(e);
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			state.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			state.mouseReleased(e);
		}
		
	};

	/**
	 * Constructor.
	 * 
	 * @param model model
	 * @param state state
	 * @param group group
	 * @param fgColor foreground color provider
	 * @param bgColor background color provide
	 */
	public JDrawingCanvas(DrawingModel model, Tool state, ButtonGroup group, IColorProvider fgColor, IColorProvider bgColor) {
		this.fgColor = fgColor;
		this.bgColor = bgColor;
		this.model = model;
		this.state = state;
		this.group = group;
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		model.addDrawingModelListener(this);
		
		setToggleButtonListeners();
	}

	/**
	 * Adds listeners to buttons from button group.
	 */
	private void setToggleButtonListeners() {
		for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
			JToggleButton button = (JToggleButton) buttons.nextElement();

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (button.getActionCommand().equals("Line")) {
						state = new AddLine(fgColor, model);
					} else if (button.getActionCommand().equals("Circle")) {
						state = new AddCircle(fgColor, model);
					} else if (button.getActionCommand().equals("FilledCircle")) {
						state = new AddFilledCircle(fgColor, bgColor, model);
					}
				}
			});
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		painter = new GeometricalObjectPainter((Graphics2D)g);	
		
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		
		state.paint((Graphics2D)g);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
}
