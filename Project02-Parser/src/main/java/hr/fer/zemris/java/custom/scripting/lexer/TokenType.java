package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Types of token that token that are legal.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public enum TokenType {
	
	/** Says there is no more tokens. */
	EOF,
	/** Echo tag that starts with "=". */
	ECHOEQUALS,
	/** Echo tag that starts with variable. */
	ECHOVARIABLE,
	/** FOR tag. */
	FOR, 
	/** Text tag. */
	TEXT, 
	/** END tag. */
	END
}
