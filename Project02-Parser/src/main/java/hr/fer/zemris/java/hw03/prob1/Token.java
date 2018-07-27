package hr.fer.zemris.java.hw03.prob1;

/**
 * Class that shapes one token.
 * 
 * @author Luka Grgić
 * @version 1.0  
 */
public class Token {

	/** Type of token */
	private TokenType tokenType;
	/** Value of toke */
	private Object value;

	/**
	 * Constructor that accepts type and value.
	 * 
	 * @param tokenType Token type.
	 * @param value Token Value.
	 */
	public Token(TokenType tokenType, Object value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 * @return Returns token value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return Returns token type.
	 */
	public TokenType getType() {
		return tokenType;
	}
}