package hr.fer.zemris.java.hw03.prob1;

/**
 * States that lexer can be in. Each state makes lexer
 * to look at data differently and process it accordingly.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public enum LexerState {
	
	/** Starting state */
	BASIC, 
	/** State in between "#" symbols */
	EXTENDED
}
