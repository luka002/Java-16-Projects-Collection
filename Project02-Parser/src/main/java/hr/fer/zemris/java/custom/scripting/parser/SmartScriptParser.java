package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * Class that creates node tree from provided text.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SmartScriptParser {

	/** Lexer that creates tokens */
	private SmartScriptingLexer lexer;
	/** Main tree node */
	private DocumentNode docmentNode;
	/** Stack to help create tree */
	private ObjectStack stack;
	/** Current token */
	private Token token;

	/**
	 * Constructor that initializes lexer with text.
	 * 
	 * @param text Provided document.
	 */
	public SmartScriptParser(String text) {
		try {
			lexer = new SmartScriptingLexer(text);
		} catch (SmartScriptingLexerException e) {
			throw new SmartScriptParserException("Lexer can't be initialized.", e);
		}
		docmentNode = new DocumentNode();
		stack = new ObjectStack();
		parseText();
	}
 
	/**
	 * @return Main node.
	 */
	public DocumentNode getDocumentNode() {
		return docmentNode;
	}

	/**
	 * Method that creates node tree based on specific tags
	 * that token holds.
	 */
	private void parseText() {
		stack.push(docmentNode);
		Node node;

		try {
			token = lexer.nextToken();
			
			while (token.getType() != TokenType.EOF) {

				if (token.getType() == TokenType.TEXT || token.getType() == TokenType.ECHOEQUALS
						|| token.getType() == TokenType.ECHOVARIABLE) {
					node = (Node) stack.peek();
					node.addChildNode(token.getValue());
				} else if (token.getType() == TokenType.FOR) {
					node = (Node) stack.peek();
					node.addChildNode(token.getValue());

					stack.push(token.getValue());
				} else if (token.getType() == TokenType.END) {
					stack.pop();
					if (stack.isEmpty()) {
						throw new SmartScriptParserException("Stack can't be empty.");
					}
				} else {
					throw new SmartScriptParserException("There was an error.");
				}
				
				token = lexer.nextToken();
			}
			
			if (stack.size() != 1) {
				throw new SmartScriptParserException("Missing END tag.");
			}
		} catch (Exception e) {
			throw new SmartScriptParserException("There was an error.", e);
		}
	}

}
