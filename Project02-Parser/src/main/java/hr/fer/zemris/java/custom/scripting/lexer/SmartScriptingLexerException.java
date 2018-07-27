package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Thrown to indicate given data can't be tokenized.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SmartScriptingLexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs an SmartScriptingLexerException with no
     * detail message.
     */
	public SmartScriptingLexerException() {
		super();
	}

	/**
     * Constructs an SmartScriptingLexerException with the
     * specified detail message.
     *
     * @param arg0 The detail message.
     */
	public SmartScriptingLexerException(String arg0) {
		super(arg0);
	}

	/**
     * Constructs an SmartScriptingLexerException with the
     * specified detail message and cause.
     *
     * @param arg0 The detail message.
     * @param arg1 cause for exception.
     */
	public SmartScriptingLexerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
     * Constructs an SmartScriptingLexerException with the
     * specified cause.
     *
     * @param arg0 cause for exception.
     */
	public SmartScriptingLexerException(Throwable arg0) {
		super(arg0);
	}
	
}
