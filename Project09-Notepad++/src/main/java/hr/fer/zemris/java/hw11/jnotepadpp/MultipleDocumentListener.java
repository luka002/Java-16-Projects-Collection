package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that should be implemented if
 * needed to listen to MultipleDocumentModel changes.
 * 
 * @author Luka Grgić
 */
public interface MultipleDocumentListener {

	/**
	 * Called when current document has changed.
	 * 
	 * @param previousModel old document
	 * @param currentModel new document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when document added
	 * 
	 * @param model document added
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when document removed.
	 * 
	 * @param model document removed
	 */
	void documentRemoved(SingleDocumentModel model);
	
}