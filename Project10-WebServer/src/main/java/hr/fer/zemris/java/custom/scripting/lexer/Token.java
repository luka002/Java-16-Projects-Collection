package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

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
	private Node value;

	/**
	 * Constructor that accepts type and value.
	 * 
	 * @param tokenType Token type.
	 * @param value Token Value.
	 */
	public Token(TokenType tokenType, Node value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 * @return Returns token value.
	 */
	public Node getValue() {
		return value;
	}

	/**
	 * @return Returns token type.
	 */
	public TokenType getType() {
		return tokenType;
	}
}
