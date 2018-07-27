package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.components.SelectedColors;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.models.MyDrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.tools.AddLine;

/**
 * Program that allows user to draw lines, circles and filled
 * circles. User can also choose color. Drawings can be saved and
 * opened as .jvd format file and also can be exported as .jpg, .png
 * and .gif format.
 * 
 * @author Luka Grgić
 *
 */
public class JVDraw extends JFrame{
	
	/** 
	 * Auto generated 
	 */
	private static final long serialVersionUID = 2665965485215869161L;
	/** 
	 * State that shows which object will be drawn 
	 */
	private Tool state;
	/** 
	 * Mouse adapter that allows changing object value after clicked two times 
	 */
	private final MouseAdapter adapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
	        @SuppressWarnings("unchecked")
			JList<GeometricalObject> list = (JList<GeometricalObject>)e.getSource();
	        
	        if (e.getClickCount() == 2) {
	            int index = list.locationToIndex(e.getPoint());
	            
	            GeometricalObject clicked = list.getModel().getElementAt(index);
	            GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
	            
	            if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Change values", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
	            	try {
	            		editor.checkEditing();
	           		    editor.acceptEditing();
	            	} catch(RuntimeException ex) {
	            		JOptionPane.showMessageDialog(
	            				JVDraw.this, 
	        					ex.getMessage(), 
	        					"Error", 
	        					JOptionPane.ERROR_MESSAGE
	        			);
	            	}
	            }
	        } 
	    }
	};
	
		
	/**
	 * Constructor that initializes frame.
	 * 
	 * @throws HeadlessException exception
	 */
	public JVDraw() throws HeadlessException {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(50, 50);
		setSize(900, 600);
		setTitle("JVDraw");
		
		initGUI();
	}

	/**
	 * Initializes all the components of the frame.
	 */
	private void initGUI() {
		JColorArea fgColor = new JColorArea(Color.BLACK);
		JColorArea bgColor = new JColorArea(Color.WHITE);
		
		DrawingModel model = new MyDrawingModel();
		Actions actions = new Actions(model, this);
		ButtonGroup group = new ButtonGroup();
		state = new AddLine(fgColor, model);
		
		createToolbars(fgColor, bgColor, group);
		createMenus(model, actions);
		
		SelectedColors selectedColors = new SelectedColors(fgColor, bgColor);
		JDrawingCanvas canvas = new JDrawingCanvas(model, state, group, fgColor, bgColor);
		DrawingObjectListModel listModel = new DrawingObjectListModel(model);
		
		JList<GeometricalObject> list = new JList<>(listModel);
		list.addMouseListener(adapter);
		list.addKeyListener(getKeyAdapter(model));
		
		JPanel child = new JPanel(new BorderLayout());
		child.add(canvas, BorderLayout.CENTER);
		child.add(selectedColors, BorderLayout.PAGE_END);
		child.add(new JScrollPane(list), BorderLayout.LINE_END);
			
		getContentPane().add(child, BorderLayout.CENTER);
	
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				actions.close();
			}
		});
	}

	/**
	 * Creates KeyAdapter that responds to '-', '+' and 
	 * 'DELETE' keys. If '-' is presses, selected value in the
	 * list will be shifted one place down, if '+' is pressed,
	 * selected value in the list will be shifted one place up and if 
	 * 'DELETE' is pressed, selected value will be deleted.
	 * 
	 * @param model model that stores geometrical objects
	 * @return KeyAdapter
	 */
	private KeyListener getKeyAdapter(DrawingModel model) {
		return new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>)e.getSource();
				int index = list.getSelectedIndex();
				
				GeometricalObject clicked = list.getModel().getElementAt(index);
				
				if (e.getKeyChar() == '+') {
					model.changeOrder(clicked, -1);
					
					if (index != 0) {
						list.setSelectedIndex(index-1);
					}
				} else if (e.getKeyChar() == '-') {
					model.changeOrder(clicked, 1);
					
					if (index != model.getSize()-1) {
						list.setSelectedIndex(index+1);
					}
				} else if (e.getKeyChar() == 127) {
					model.remove(clicked);
					if (index != 0) {
						list.setSelectedIndex(index-1);
					} else {
						list.setSelectedIndex(index);
					}
				}
			}
			
		};
	}

	/**
	 * Creates menus for the frame.
	 * 
	 * @param model model that stores geometrical objects
	 * @param actions actions
	 */
	private void createMenus(DrawingModel model, Actions actions) {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(actions.openAction));
		fileMenu.add(new JMenuItem(actions.saveAction));
		fileMenu.add(new JMenuItem(actions.saveAsAction));
		fileMenu.add(new JMenuItem(actions.exportAction));
		fileMenu.add(new JMenuItem(actions.exitAction));
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Creates toolbar for the frame.
	 * 
	 * @param fgColor foreground color for drawing
	 * @param bgColor background color for drawing
	 * @param group button group
	 */
	private void createToolbars(JColorArea fgColor, JColorArea bgColor, ButtonGroup group) {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		
		toolBar.add(fgColor);
		toolBar.addSeparator(new Dimension(5, 5));
		toolBar.add(bgColor);
		toolBar.addSeparator(new Dimension(5, 5));
		
		JToggleButton line = new JToggleButton("Line");
		JToggleButton circle = new JToggleButton("Circle");
		JToggleButton filledCircle = new JToggleButton("FilledCircle");
		
		group.add(line);
		group.add(circle);
		group.add(filledCircle);
		group.setSelected(line.getModel(), true);
		
		toolBar.add(line);
		toolBar.addSeparator(new Dimension(5, 5));
		toolBar.add(circle);
		toolBar.addSeparator(new Dimension(5, 5));
		toolBar.add(filledCircle);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * First method executed when program starts.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
	
}
