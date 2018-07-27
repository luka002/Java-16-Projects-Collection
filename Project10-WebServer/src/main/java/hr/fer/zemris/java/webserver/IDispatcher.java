package hr.fer.zemris.java.webserver;

/**
 * Interface with method that is called
 * to send answer to client.
 * 
 * @author Luka Grgić
 *
 */
public interface IDispatcher {
	
	/**
	 * Method that is called to send answer to client.
	 * 
	 * @param urlPath path
	 * @throws Exception exception
	 */
	void dispatchRequest(String urlPath) throws Exception;

}