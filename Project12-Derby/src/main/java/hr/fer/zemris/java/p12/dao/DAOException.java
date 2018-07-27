package hr.fer.zemris.java.p12.dao;

/**
 * Thrown to indicate that error occurred while trying
 * to fetch data from database.
 * 
 * @author Luka Grgić
 */
public class DAOException extends RuntimeException {

	/**
	 * Auto generated
	 */
	private static final long serialVersionUID = -1148873098273181672L;

	/**
	 * Default constructor.
	 */
	public DAOException() {
		super();
	}

	/**
	 * Constructor with specified error message and cause.
	 * 
	 * @param message error message
	 * @param cause cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with specified error message.
	 * 
	 * @param message error message
	 */
	public DAOException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with specified cause.
	 * 
	 * @param cause cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}