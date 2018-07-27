package hr.fer.zemris.java.hw03.prob1;

/**
 * Types of token that token that are legal.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public enum TokenType {
	
	/** Says there is no more tokens. */
	EOF, 
	/** Word */
	WORD, 
	/** Long number */
	NUMBER, 
	/** Symbol */
	SYMBOL
}
