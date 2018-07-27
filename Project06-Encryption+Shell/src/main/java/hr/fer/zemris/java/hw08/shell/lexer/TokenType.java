package hr.fer.zemris.java.hw08.shell.lexer;

/**
 * Enumeration of token types for a smart script lexer.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public enum TokenType {
	/** End of file */
	EOF,
	/** Outside tag */
	STRING,
	/** Inside tag */
	INSIDE_TAG
}
