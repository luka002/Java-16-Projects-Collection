package hr.fer.zemris.java.webserver;

/**
 * Interface for any object that can process current request.
 * 
 * @author Luka Grgić
 */
public interface IWebWorker {
	
	/**
	 * Method that gets RequestContext as parameter and 
	 * it is expected to create a content for client.
	 * 
	 * @param context context 
	 * @throws Exception exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}