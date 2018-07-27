package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface represents a model capable of holding zero, one or more documents,
 * where each document and having a concept of current document – the one which is shown to the
 * user and on which user works.
 *
 * @author Luka Grgić
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates new SingleDocumentModel and adds it to
	 * this model then creates new tab and adds it to tab.
	 * 
	 * @return created SingleDocumentModel
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns current document.
	 * 
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Creates document from given path and
	 * adds it to new tab.
	 * 
	 * @param path path of the document
	 * @return created document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves model data to provided newPath.
	 * 
	 * @param model model to be saved
	 * @param newPath path to be saved to
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Removes model from this model and removes tab
	 * associated with it.
	 * 
	 * @param model model to be removed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds MultipleDocumentListener to this model.
	 * 
	 * @param l MultipleDocumentListener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes MultipleDocumentListener from this model.
	 * 
	 * @param l MultipleDocumentListener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Return number of documents.
	 * 
	 * @return number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Return document at specified index
	 * 
	 * @param index index where document is
	 * @return document ad specified index
	 */
	SingleDocumentModel getDocument(int index);
	
}