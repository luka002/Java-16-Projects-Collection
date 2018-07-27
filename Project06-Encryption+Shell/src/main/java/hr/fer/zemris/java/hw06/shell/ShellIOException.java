package hr.fer.zemris.java.hw06.shell;

/**
 * Class that represents exception that is thrown when
 * error with user interaction happens.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Defeault constructor.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Constructor that receives a message.
	 * 
	 * @param message Message shown when exception is thrown. 
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
}
