package hr.fer.zemris.java.hw08.shell.lexer;

/**
 * Exception representing that something went wrong within a Lexer.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LexerException extends RuntimeException{

	/**
	 * Initializes an exception.
	 */
	LexerException() {
		super();
	}

	/**
	 * Initializes an exception with an given message.
	 * 
	 * @param message exception message
	 */
	LexerException(String message) {
		super(message);
	}

	
}
