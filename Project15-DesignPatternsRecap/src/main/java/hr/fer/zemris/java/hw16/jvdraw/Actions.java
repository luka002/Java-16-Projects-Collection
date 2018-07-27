package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Class that holds all of the actions that are
 * used in JVDraw. It is also a listener in the 
 * observer design pattern. It listens to model
 * for the changes so it knows if exit action
 * should ask for save before closing. There are
 * 5 actions: openAction, saveAction, saveAsAction,
 * exportAction and exitAction.
 * 
 * @author Luka Grgić
 *
 */
public class Actions implements DrawingModelListener {
	
	/**
	 * Model.
	 */
	private DrawingModel model;
	/**
	 * Frame.
	 */
	private JFrame frame;
	/**
	 * Path to current open file.
	 */
	private Path path;
	/**
	 * Is model changed.
	 */
	private boolean modelChanged;
	
	/**
	 * Constructor.
	 * 
	 * @param model model
	 * @param frame frame
	 */
	public Actions(DrawingModel model, JFrame frame) {
		this.model = model;
		this.frame = frame;
		this.path = null;
		this.modelChanged = false;
		
		openAction.putValue(Action.NAME, "Open");
		saveAction.putValue(Action.NAME, "Save");
		saveAsAction.putValue(Action.NAME, "SaveAs");
		exportAction.putValue(Action.NAME, "Export");
		exitAction.putValue(Action.NAME, "Exit");
		
		model.addDrawingModelListener(this);
	}

	/**
	 * Action for opening file.
	 */
	public Action openAction = new AbstractAction() {
		
		/**
		 * Auto generated
		 */
		private static final long serialVersionUID = -1481816922882213208L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jvd", "jvd");
			fc.setFileFilter(filter);
			
			if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();		
			
			if (!endsWithJVD(filePath.toString())) {
				return;
			}
			
			List<String> lines = null;
			try {
				lines = Files.readAllLines(filePath);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(
						frame,
						"Error reading file",
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			writeLinesToModel(lines);
			path = filePath;
			modelChanged = false;
		}

		/**
		 * Writes all objects from file to the model.
		 * 
		 * @param lines objects in string notation
		 */
		private void writeLinesToModel(List<String> lines) {
			for (String line : lines) {
				String[] values = line.split(" ");
				
				if (values[0].equals("LINE")) {
					Color fgColor = getColor(values[5], values[6], values[7]);
					
					model.add(new Line(
							Integer.parseInt(values[1]), 
							Integer.parseInt(values[2]), 
							Integer.parseInt(values[3]), 
							Integer.parseInt(values[4]), 
							fgColor
					));
				
				} else if (values[0].equals("CIRCLE")) {
					Color fgColor = getColor(values[4], values[5], values[6]);
					
					model.add(new Circle(
							Integer.parseInt(values[1]), 
							Integer.parseInt(values[2]), 
							Integer.parseInt(values[3]),  
							fgColor
					));
					
				} else if (values[0].equals("FCIRCLE")) {
					Color fgColor = getColor(values[4], values[5], values[6]);
					Color bgColor = getColor(values[7], values[8], values[9]);
					
					model.add(new FilledCircle(
							Integer.parseInt(values[1]), 
							Integer.parseInt(values[2]), 
							Integer.parseInt(values[3]),  
							fgColor,
							bgColor
					));
				}
			}
		}
		
		/**
		 * Decodes color from r, g, b string to color.
		 * 
		 * @param r red value of color
		 * @param g green value of color
		 * @param b blue value of color
		 * @return color
		 */
		private Color getColor(String r, String g, String b) {
			return Color.decode(
					String.format("#%02X%02X%02X", 
							Integer.parseInt(r), 
							Integer.parseInt(g), 
							Integer.parseInt(b)
					).toString()
			);
		}
	};
	
	/**
	 * Checks if file name ends with .jvd.
	 * 
	 * @param name file name
	 * @return true if name ends with .jvd else false
	 */
	private boolean endsWithJVD(String name) {
		if (!name.endsWith(".jvd")) {
			JOptionPane.showMessageDialog(
					frame,
					"Wrong extension",
					"Error",
					JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Action for saving current file.
	 */
	public Action saveAction = new AbstractAction() {
		
		/**
		 * Auto generated.
		 */
		private static final long serialVersionUID = 492645579885460943L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (path != null) {
				save(path);
			} else {
				saveAs();
			}
		}
		
	};

	/**
	 * Action for choosing file name and saving.
	 */
	public Action saveAsAction = new AbstractAction() {
		
		/**
		 * Auto generated.
		 */
		private static final long serialVersionUID = 3800061841901554891L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveAs();
		}

	};

	/**
	 * Asks user where to save file and saves it.
	 */
	private void saveAs() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jvd", "jvd");
		jfc.setFileFilter(filter);
		
		if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					frame, 
					"Nothing is saved", 
					"Warning",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		
		File fileName = jfc.getSelectedFile();
		Path filePath = fileName.toPath();		
		
		if (!endsWithJVD(filePath.toString())) {
			return;
		}
		
		if (Files.exists(filePath)) {
			int dialogResult = JOptionPane.showConfirmDialog(
					frame,
					"File already exists, do you wish to overwrite it?", 
					"Overwrite exsisting file",
					JOptionPane.YES_NO_OPTION
			);
			
			if (dialogResult == JOptionPane.NO_OPTION) {
				return;
			}
		
		} else {
			try {
				Files.createFile(jfc.getSelectedFile().toPath());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(
						frame, 
						"Can not create file: " + filePath.toString(), 
						"Warning",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}
		}
		
		save(filePath);
	}

	/**
	 * Saves file to the provided filePath.
	 * 
	 * @param filePath file path
	 */
	private void save(Path filePath) {
		try {
			Files.write(filePath, model.toString().getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(
					frame, 
					"Error while writing file", 
					"Error", 
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		JOptionPane.showMessageDialog(
				frame, 
				"File saved", 
				"Information", 
				JOptionPane.INFORMATION_MESSAGE
		);
		
		path = filePath;
		modelChanged = false;
	}
	
	/**
	 * Action that exports model as image.
	 */
	public Action exportAction = new AbstractAction() {
		
		/**
		 * Auto generated.
		 */
		private static final long serialVersionUID = -6861440121003813059L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Export document");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.png, *.gif, *.jpg", "png", "gif", "jpg");
			jfc.setFileFilter(filter);
			
			if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						frame, 
						"Nothing is exported", 
						"Warning",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}
			
			File fileName = jfc.getSelectedFile();
			Path filePath = fileName.toPath();		
			
			if (wrongExtension(filePath.toString())) {
				return;
			}
			
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for(int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(bbcalc);
			}
			Rectangle box = bbcalc.getBoundingBox();
			
			BufferedImage image = new BufferedImage(
					box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
			);
			Graphics2D g = image.createGraphics();
			g.translate(-box.x, -box.y);
			
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			for(int i = 0; i < model.getSize(); i++) {
				model.getObject(i).accept(painter);
			}
			
			g.dispose();
			
			int extentsion = fileName.toString().lastIndexOf('.');
			try {
				ImageIO.write(image, fileName.toString().substring(extentsion+1), fileName);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						frame, 
						"Error while writing file", 
						"Error", 
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			JOptionPane.showMessageDialog(
					frame, 
					"File saved", 
					"Information", 
					JOptionPane.INFORMATION_MESSAGE
			);
		}

		/**
		 * Checks if filePath ends with .png,
		 * .gif or .jpg.
		 * 
		 * @param filePath file path
		 * @return true if doesn't end with .png,
		 * .gif or .jpg. else false
		 */
		private boolean wrongExtension(String filePath) {
			if (!filePath.endsWith(".png") &&
					!filePath.endsWith(".gif") && 
					!filePath.endsWith(".jpg")) {
				
				JOptionPane.showMessageDialog(
						frame,
						"Wrong extension",
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
				return true;
			}
			
			return false;
		}

	};
	
	/**
	 * Action for closing the program.
	 */
	public Action exitAction = new AbstractAction() {
		
		/**
		 * Auto generated.
		 */
		private static final long serialVersionUID = -2586294861213868780L;

		@Override
		public void actionPerformed(ActionEvent e) {
			close();
		}
		
	};

	/**
	 * Asks user if he wants to save changed model
	 * and exits the program.
	 */
	public void close() {
		if (modelChanged) {
			int dialogResult = JOptionPane.showConfirmDialog(
					frame, 
					(path == null ? "File" : "\"" + path.getFileName() + "\"") + " not saved. Save before closing?",
					"Save", 
					JOptionPane.YES_NO_CANCEL_OPTION
			);

			if (dialogResult == JOptionPane.YES_OPTION && path != null) {
				save(path);
			} else if (dialogResult == JOptionPane.YES_OPTION && path == null){
				saveAs();
			} else if (dialogResult == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		
		frame.dispose();
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		modelChanged = true;
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		modelChanged = true;
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		modelChanged = true;
	}
	
}
