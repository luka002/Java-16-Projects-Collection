package hr.fer.zemris.java.hw03.prob1;

/**
 * Class represents lexical analyzer that analyzes given data
 * based on set of rules. Data should be made of words, numbers(Long) and symbols.
 * When lexer comes across "#" symbol different set of rules apply.
 * Before "#" symbol every word, number and symbol produces a Token.
 * After "#" everything is looked as one word. 
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Lexer {
	
	/** Data that will be analyzed. */
	private char[] data;
	/** Current token produced by lexer. */
	private Token token;
	/** Index of first unprocessed character.*/
	private int currentIndex;
	/** Current state of lexer. */
	private LexerState state;

	/**
	 * Constructor that receives data to be processed.
	 * 
	 * @param text Data that will be processed.
	 * @throws IllegalArgumentException if text is null.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input can not be null.");
		}
		
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}

	/**
	 * @return Returns last produced token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets lexers state.
	 * 
	 * @param state State that is set for lexer.
	 * @throws IllegalArgumentException if state is null.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State can't be null.");
		}
		
		this.state = state;
	}

	/**
	 * Method that generates next token from data.
	 * 
	 * @return Generated token.
	 * @throws LexerException if next token is invalid.
	 */
	public Token nextToken() {
		if (token != null && getToken().getType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}

		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		char character = data[currentIndex];
		
		if (character == '#') {
			if (state == LexerState.BASIC) {
				state = LexerState.EXTENDED;
			} else {
				state = LexerState.BASIC;
			}
			
			return symbol();
		}
		
		if (state == LexerState.BASIC) {
			if (Character.isLetter(character) || character == '\\') {
				return word(character);
			} else if (Character.isDigit(character)) {
				return number(character);
			}
			return symbol();
		} else {
			if (character != '#') {
				return specialWord(character);
			}
			
			return symbol();
		}
		
	}

	/**
	 * Method that produces "word" token.
	 * 
	 * @param character First character of token.
	 * @return Produced token.
	 * @throws LexerException if token is invalid.
	 */
	private Token word(char character) {
		StringBuilder word = new StringBuilder();
		
		while (currentIndex < data.length && (Character.isLetter(character) || character == '\\')) {
			if (Character.isLetter(character)) {
				word.append(character);
				currentIndex++;
			} else if (character == '\\') {
				currentIndex++;
				if (currentIndex < data.length) {
					character = data[currentIndex];	
				}
				
				if (character == ' ' || character == '\t' || character == '\r' || 
						character== '\n' || currentIndex >= data.length) {
					throw new LexerException("\"\\\" can't precede empty space "
							+ "and it can't be last character of the input.");
				}
				
				if (Character.isLetter(character)) {
					throw new LexerException("\"\\\" can't precede letter.");
				}
				
				word.append(character);
				currentIndex++;
			}
			
			if (currentIndex < data.length) {
				character = data[currentIndex];	
			}
			
		}
		
		token = new Token(TokenType.WORD, new String(word));
		return token;
	}
	
	/**
	 * Method that produces "number" token.
	 * 
	 * @param character First digit of token.
	 * @return Produced token.
	 * @throws LexerException if token is invalid.
	 */
	private Token number(char digit) {
		StringBuilder number = new StringBuilder();
		
		while (currentIndex < data.length && Character.isDigit(digit)) {
			number.append(digit);
			currentIndex++;
			
			if (currentIndex < data.length) {
				digit = data[currentIndex];
			}
		}
		
		try {
			token = new Token(TokenType.NUMBER, Long.valueOf(number.toString()));
			return token;
		} catch (NumberFormatException e) {
			throw new LexerException("Number can't fit in long");
		}
		
	}

	/**
	 * Method that produces "symbol" token.
	 * 
	 * @return Produced token.
	 */
	private Token symbol() {
		token = new Token(TokenType.SYMBOL, (char) data[currentIndex]);
		currentIndex++;
		return token;
	}
	
	/**
	 * Method that produces token between "#" symbols.
	 * 
	 * @param character First character of token.
	 * @return Produced token.
	 */
	private Token specialWord(char character) {
		StringBuilder word = new StringBuilder();
		
		while (character != ' ' && character != '\t' && character != '\r' && 
				character != '\n' && currentIndex < data.length && character != '#' ) {
			word.append(character);
			currentIndex++;
			
			if (currentIndex < data.length) {
				character = data[currentIndex];	
			}
		}
		
		token = new Token(TokenType.WORD, word.toString());
		return token;
	}
	
	/**
	 * Helper method for skipping blanks in data.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}