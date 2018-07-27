package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class extends JTabbedPane and it stored list of
 * SingleDocumentModels with each of them assigned one tab.
 * Tabs can be added and removed dynamically. Documents can be
 * created as blank documents or they can be loaded from file
 * system. They can also be saved.
 * 
 * @author Luka Grgić
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of models
	 */
	private List<SingleDocumentModel> models;
	/**
	 * Current model
	 */
	private SingleDocumentModel currentModel;
	/**
	 * List of listeners
	 */
	private List<MultipleDocumentListener> listeners;
	/**
	 * Map associating tab component with its name
	 */
	private Map<JScrollPane, String> tabNames;
	
	/**
	 * Constructor initializing variables and
	 * adding change listener to change title.
	 * 
	 * @param frame frame
	 */
	public DefaultMultipleDocumentModel(JFrame frame) {
		super(JTabbedPane.TOP);
		models = new ArrayList<>();
		currentModel = null;
		listeners = new ArrayList<>();
		tabNames = new HashMap<>();
		
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if (tabNames.size() > 0) {
					String name = tabNames.get((JScrollPane) getSelectedComponent());
					frame.setTitle(name + " - JNotepad++");				
				} else {
					frame.setTitle("JNotepad++");
				}
				
				if (getSelectedIndex() >= 0) {
					currentModel = models.get(getSelectedIndex());
				} else {
					currentModel = null;
				}
				
				for (MultipleDocumentListener listener : listeners) {
					listener.currentDocumentChanged(null, currentModel);	
				}
			}
		});
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return models.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return createModel(null, "");
	}
	
	/**
	 * Creates ImageIcon object.
	 * 
	 * @param name image name
	 * @return ImageIcon object
	 */
	private ImageIcon createIcon(String name) {
		try (InputStream is = this.getClass().getResourceAsStream(name)) {
			if (is == null) {
				return null;
			}
			
			byte[] bytes = is.readAllBytes();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Creates model with given path and text and
	 * adds listener to it.
	 * 
	 * @param path path
	 * @param text text
	 * @return SingleDocumentModel
	 */
	private SingleDocumentModel createModel(Path path, String text) {
		SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		models.add(model);
		

		JScrollPane sPane = new JScrollPane(model.getTextComponent());
		ImageIcon icon = createIcon("green.png");

		if (path == null) {
			tabNames.put(sPane, "Untitled");
			addTab("Untitled", icon, sPane);
			setToolTipTextAt(indexOfComponent(sPane), "Untitled");
		}
		else {
			tabNames.put(sPane, path.toAbsolutePath().toString());
			addTab(model.getFilePath().getFileName().toString(), icon, sPane);
			setToolTipTextAt(indexOfComponent(sPane), model.getFilePath().toAbsolutePath().toString());
		}

		setSelectedIndex(getTabCount() - 1);
		
		model.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(indexOfComponent(getSelectedComponent()), createIcon("red.png"));
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				SwingUtilities.invokeLater(() -> {
					setTitleAt(indexOfComponent(getSelectedComponent()), 
							model.getFilePath().getFileName().toString());
					
					setToolTipTextAt(indexOfComponent(getSelectedComponent()), 
							model.getFilePath().toAbsolutePath().toString());
				});
			}
		});

		for (MultipleDocumentListener listener : listeners) {
			listener.documentAdded(model);
		}
		
		currentModel = model;
		return model;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentModel;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path can not be null.");
		
		for (SingleDocumentModel model : models) {
			
			if (model.getFilePath() != null && model.getFilePath().equals(path)) {
				setSelectedIndex(models.indexOf(model));
				return model;
			}
			
		}
		
		byte[] octets = null;
		try {
			octets = Files.readAllBytes(path);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this,
					"Error while reading file " + path.toAbsolutePath(),
					"Error",
					JOptionPane.ERROR_MESSAGE
			);
			return null;
		}
		
		return createModel(path, new String(octets, StandardCharsets.UTF_8));		
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		try {
			Files.write(newPath, data);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					this, 
					"Error while writing file", 
					"Error", 
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		JOptionPane.showMessageDialog(
				this, 
				"File saved", 
				"Information", 
				JOptionPane.INFORMATION_MESSAGE
		);
		
		
		model.setFilePath(newPath);
		tabNames.put((JScrollPane) getSelectedComponent(), newPath.toAbsolutePath().toString());
		setIconAt(indexOfComponent(getSelectedComponent()), createIcon("green.png"));
		fireStateChanged();
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		tabNames.remove((JScrollPane) getSelectedComponent());
		models.remove(model);	
		remove(getSelectedIndex());	
		
		if (models.size() > 0) {
			setSelectedIndex(models.size()-1);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return models.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return models.get(index);
	}
	
}
