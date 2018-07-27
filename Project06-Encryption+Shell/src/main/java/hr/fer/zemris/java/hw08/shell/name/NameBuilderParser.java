package hr.fer.zemris.java.hw08.shell.name;

import hr.fer.zemris.java.hw08.shell.lexer.Lexer;
import hr.fer.zemris.java.hw08.shell.lexer.Token;
import hr.fer.zemris.java.hw08.shell.lexer.TokenType;

/**
 * Parses given expression.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class NameBuilderParser {
	/** NameBuilder that stores other NameBuilders */
	private MainNameBuilder nameBuilder;
	/** Lexer */
	private Lexer lexer;
	
	/**
	 * Constructor.
	 * 
	 * @param expression Expression that is parsed.
	 */
	public NameBuilderParser(String expression) {
		lexer = new Lexer(expression);
		nameBuilder = new MainNameBuilder();
		parse();
	}
	  
	/**
	 * Parses expression.
	 */
	private void parse() {
		Token token = lexer.nextToken();
		
		while (token.getType() != TokenType.EOF) {
			nameBuilder.addNameBuilder((NameBuilder)token.getValue());
			token = lexer.nextToken();
		}
	}

	/**
	 * @return returns nameBuilder.
	 */
	public MainNameBuilder getNameBuilder() {
		return nameBuilder;
	}
	
}
