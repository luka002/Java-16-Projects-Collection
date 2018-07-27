package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that should be implemented if
 * needed to listen to SingleDocumentModel changes.
 * 
 * @author Luka Grgić
 */
public interface SingleDocumentListener {
	
	/**
	 * Called if modification occurs.
	 * 
	 * @param model model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Called if file path updated.
	 * 
	 * @param model model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
	
}
