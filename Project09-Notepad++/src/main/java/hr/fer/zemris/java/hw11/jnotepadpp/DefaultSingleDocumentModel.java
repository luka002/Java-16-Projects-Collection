package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Concrete SingleCocumentModel implementation.
 * 
 * @author Luka Grgić
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	/**
	 * Model path
	 */
	private Path path;
	/**
	 * Model text area
	 */
	private JTextArea editor;
	/**
	 * Modification flag
	 */
	private boolean modified;
	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor initializes variables
	 * and adds document listener to editor.
	 * 
	 * @param path path
	 * @param text text given to text area
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		super();
		this.path = path;
		this.modified = false;
		this.listeners = new ArrayList<>();
		this.editor = new JTextArea(text);
		this.editor.getDocument().addDocumentListener(new MyDocumentListener());
	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "Can not be null.");
		this.path = path;
		
		for (SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		for (SingleDocumentListener listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Class that represents document listener and
	 * with every modification sets modified flag to true.
	 * 
	 * @author Luka Grgić
	 */
	private class MyDocumentListener implements DocumentListener {
		
		@Override
		public void removeUpdate(DocumentEvent event) {
			setModified(true);
		}
		
		@Override
		public void insertUpdate(DocumentEvent event) {
			setModified(true);
		}
		
		@Override
		public void changedUpdate(DocumentEvent event) {
			setModified(true);
		}
	}

}
