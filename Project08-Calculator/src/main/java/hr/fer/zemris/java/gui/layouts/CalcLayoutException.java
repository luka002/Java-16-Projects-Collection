package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown to indicate wrong data provided to CalcLayout.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CalcLayoutException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs an CalcLayoutException with no
     * detail message.
     */
	public CalcLayoutException() {
		super();
	}

	/**
     * Constructs an CalcLayoutException with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
     * Constructs an CalcLayoutException with the
     * specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause cause for exception.
     */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
     * Constructs an CalcLayoutException with the
     * specified cause.
     *
     * @param cause cause for exception.
     */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
}