package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that represents exception that is thrown when
 * invalid action with stack is made.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Defeault constructor.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor that receives a message.
	 * 
	 * @param message Message shown when exception is thrown. 
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
}
