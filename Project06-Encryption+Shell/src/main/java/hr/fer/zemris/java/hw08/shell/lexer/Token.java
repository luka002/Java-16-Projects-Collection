package hr.fer.zemris.java.hw08.shell.lexer;

/**
 * Represents one token for a lexer.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Token {
	/** Token type */
	private TokenType type;
	/** Token value */
	private Object value;
	
	/**
	 * Initializes the token.
	 * 
	 * @param type token type
	 * @param value token value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
}
