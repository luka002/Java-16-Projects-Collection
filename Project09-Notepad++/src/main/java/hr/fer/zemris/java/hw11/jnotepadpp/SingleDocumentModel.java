package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that represents a model of single document, having 
 * information about file path from which document was loaded 
 * (can be null for new document), document modification 
 * status and reference to Swing component which is used for
 * editing (each document has its own editor component).
 * 
 * @author Luka Grgić
 */
public interface SingleDocumentModel {

	/**
	 * Returns text area component of this model.
	 * 
	 * @return text area component 
	 */
	JTextArea getTextComponent();

	/**
	 * Return file path of this model.
	 * @return file path of this model
	 */
	Path getFilePath();

	/**
	 * Sets file path.
	 * 
	 * @param path file path to be set
	 */
	void setFilePath(Path path);

	/**
	 * Checks if model has been modified.
	 * 
	 * @return true if it has been modified, else false
	 */
	boolean isModified();

	/**
	 * Sets if it has been modified or not.
	 * 
	 * @param modified modification flag
	 */
	void setModified(boolean modified);

	/**
	 * Adds SingleDocumentListener to this model.
	 * 
	 * @param l SingleDocumentListener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes SingleDocumentListener from this model
	 * 
	 * @param l SingleDocumentListener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
	
}