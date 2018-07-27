package hr.fer.zemris.java.hw08.shell.lexer;

import hr.fer.zemris.java.hw08.shell.name.LiteralNameBuilder;
import hr.fer.zemris.java.hw08.shell.name.SpecialNameBuilder;

/**
 * A simple lexer for NameBuilderParser.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Lexer {
	/** Provided data. */
	private char[] data;
	/** Current token. */
	private Token token;
	/** Current index. */
	private int currentIndex;

	/** End of file token .*/
	private static Token EOF_TOKEN = new Token(TokenType.EOF, null);

	/**
	 * Initializes lexer with given text.
	 * 
	 * @param text Text.
	 */
	public Lexer(String text) {
		if (text == null)
			throw new LexerException("Can't parse null!");

		data = text.toCharArray();
	}

	/**
	 * Generates next token.
	 * 
	 * @return next token.
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException("No more tokens!");

		return nextTokenText();
	}

	/**
	 * Generates text token.
	 * 
	 * @return next text token.
	 */
	private Token nextTokenText() {
		StringBuilder value = new StringBuilder();

		while (currentIndex < data.length) {
			if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '{') {
				if (value.length() == 0) {
					currentIndex += 2;
					return nextTokenTag();
				} else {
					return new Token(TokenType.STRING, new LiteralNameBuilder(value.toString()));
				}

			}

			value.append(data[currentIndex]);
			currentIndex++;
		}

		if (value.length() == 0)
			return EOF_TOKEN;

		return new Token(TokenType.STRING, new LiteralNameBuilder(value.toString()));
	}

	/**
	 * Generates tag token.
	 * 
	 * @return tag token.
	 * @throws LexerException if error occurs.
	 */
	private Token nextTokenTag() {
		String firstNumber = null;
		String secondNumber = null;
		boolean commaFound = false;
		
		while (currentIndex < data.length) {
			char currentChar = data[currentIndex];

			if (Character.isWhitespace(currentChar)) {
				currentIndex++;

			} else if (Character.isDigit(currentChar)) {
				if (firstNumber == null) {
					firstNumber = getNextNumber();
				} else if (commaFound && secondNumber == null) {
					secondNumber = getNextNumber();
				} else {
					throw new LexerException("Wrong arguments.");
				}
				
			} else if (currentChar == ',') {
				currentIndex++;
				commaFound = true;
				
			} else if (currentChar == '}') {
					currentIndex++;
					return new Token(TokenType.INSIDE_TAG, 
							new SpecialNameBuilder(Integer.parseInt(firstNumber), secondNumber));
			
			} else {
				throw new LexerException("Error.");	
			}

		}

		throw new LexerException("Tag never closed.");
	}

	/**
	 * Gets next number from data.
	 * 
	 * @return next number from data.
	 * @throws LexerException if error occurs.
	 */
	private String getNextNumber() {
		StringBuilder number = new StringBuilder();
		
		while (currentIndex < data.length) {
			char currentChar = data[currentIndex];

			if (Character.isDigit(currentChar)) {
				number.append(currentChar);
				currentIndex++;
			
			} else {
				return number.toString();
			}
		}
		
		throw new LexerException("Tag never closed.");
	}

	/**
	 * @return current token.
	 */
	public Token getCurrentToken() {
		if (token == null) {
			nextToken();
		}

		return token;
	}

}
