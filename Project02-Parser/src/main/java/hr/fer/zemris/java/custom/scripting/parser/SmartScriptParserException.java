package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate given data can't be parsed.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs an SmartScriptParserException with no
     * detail message.
     */
	public SmartScriptParserException () {
		super();
	}

	/**
     * Constructs an SmartScriptParserException with the
     * specified detail message.
     *
     * @param arg0 The detail message.
     */
	public SmartScriptParserException (String arg0) {
		super(arg0);
	}

	/**
     * Constructs an SmartScriptParserException with the
     * specified detail message and cause.
     *
     * @param arg0 The detail message.
     * @param arg1 cause for exception.
     */
	public SmartScriptParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
     * Constructs an SmartScriptParserException with the
     * specified cause.
     *
     * @param arg0 cause for exception.
     */
	public SmartScriptParserException(Throwable arg0) {
		super(arg0);
	}
	
}
