package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class represents lexical analyzer that analyzes given data
 * based on set of rules. Given data can be processes as series of
 * tokens.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SmartScriptingLexer {

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
	 * @throws SmartScriptingLexerException if input is null.
	 */
	public SmartScriptingLexer(String document) {
		if (document == null) {
			throw new SmartScriptingLexerException("Input can not be null.");
		}
		
		data = document.toCharArray();
		currentIndex = 0;
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
	 * @throws SmartScriptingLexerException if state is null.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new SmartScriptingLexerException("State can't be null.");
		}
		
		this.state = state;
	}
	
	/**
	 * Method that generates next token from data.
	 * 
	 * @return Generated token,
	 * @throws SmartScriptingLexerException if next token is invalid.
	 */
	public Token nextToken() {
		if (token != null && getToken().getType() == TokenType.EOF) {
			throw new SmartScriptingLexerException("No tokens available.");
		}
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		currentIndex--;
		char character = getNextChar();
		String tag = null;
		
		if (character == '{') {
			character = getNextChar();
			
			if (character == '$') {
				character = getNextChar();
				
				tag = checkKeyWord(character);
			} else {
				currentIndex--;
				return text('{');
				//throw new SmartScriptingLexerException("After '{' it has to come '$'.");
			}
		} else {
			state = LexerState.TEXT;
		}
		
		if (state == LexerState.TEXT) {
			return text(character);
		} else if (state == LexerState.ECHOEQUALSTAG || state == LexerState.ECHOVARIABLETAG) {
			return echoNode(tag);
		} else if (state == LexerState.FORTAG) {
			return forNode();
		}
		return endNode();	
	}

	/**
	 * Method that produces text token.
	 * 
	 * @param character First character of token.
	 * @return Produced token.
	 * @throws SmartScriptingLexerException if token is invalid.
	 */
	private Token text(char character) {
		StringBuilder text = new StringBuilder();
		
		while (character != '{' || text.length()==0) {
			if (character == '\\') {
				character = getNextChar();
				
				if (character != '\\' && character != '{') {
					throw new SmartScriptingLexerException("In text only \\ and { can be escaped.");
				} 
			}
			
			text.append(character);
			
			try {
				character = getNextChar();
			} catch (SmartScriptingLexerException e) {
				break;
			}
		}
		
		token = new Token(TokenType.TEXT, new TextNode(text.toString()));
		return token;
	}
	
	/**
	 * Method that produces echo token.
	 * 
	 * @param tag First character of token.
	 * @return Produced token.
	 * @throws SmartScriptingLexerException if token is invalid.
	 */
	private Token echoNode(String tag) {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		char character = getNextChar();
		
		if (state == LexerState.ECHOVARIABLETAG) {
			elements.add(new ElementVariable(" " + tag));
		} else {
			elements.add(new ElementOperator("="));
		}

		while (character != '$') {
			skipBlanks();
			character = getNextChar();
			
			if (Character.isDigit(character)) {
				elements.add(getNewDigit(character));
			} else if (Character.isLetter(character)) {
				elements.add(getNewVariable(character));
			} else if (character == '@') {
				elements.add(getNewFunction());
			} else if (character == '+' || character == '-' || character == '*' ||
					character == '/' || character == '^') {
				elements.add(getNewOperator(character));
			} else if (character == '"') {
				elements.add(getNewString());
			} else {
				throw new SmartScriptingLexerException("Can not recognize element.");
			}
			
			currentIndex++;
			skipBlanks();
			character = getNextChar();
		}
		
		character = getNextChar();
		
		if (character == '}') {
			currentIndex++;
			Element[] allElements = new Element[elements.size()];
			
			for (int i = 0; i < elements.size(); i++) {
				allElements[i] = (Element)elements.get(i);
			}
			
			TokenType type;
			if (state == LexerState.ECHOEQUALSTAG) {
				type = TokenType.ECHOEQUALS;
			} else {
				type = TokenType.ECHOVARIABLE;
			}
			
			token = new Token(type, new EchoNode(allElements));
			return token;
		} else {
			throw new SmartScriptingLexerException("Tag not closed correctly.");
		}
	}

	/**
	 * Method that produces for token.
	 * 
	 * @return Produced token.
	 * @throws SmartScriptingLexerException if token is invalid.
	 */
	private Token forNode() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		char character = getNextChar();
		int parameterNumber = 0;
		
		while (character != '$') {
			skipBlanks();
			character = getNextChar();
			
			if ((parameterNumber == 0 && !Character.isLetter(character)) || parameterNumber >= 4) {
				throw new SmartScriptingLexerException("Wrong parameter number or not starting with variable.");
			} 
			
			if (Character.isDigit(character) || character == '-') {
				elements.add(getNewDigit(character));
			} else if (character == '"') {
				elements.add(getNewString());
			} else if (Character.isLetter(character)) {
				elements.add(getNewVariable(character));
			} else {
				throw new SmartScriptingLexerException("Not legal element.");
			}
			
			parameterNumber++;
			currentIndex++;
			skipBlanks();
			character = getNextChar();
		}
		
		character = getNextChar();
		
		if (character == '}') {
			if (parameterNumber == 3) {
				currentIndex++;
				token = new Token(TokenType.FOR, new ForLoopNode((ElementVariable) elements.get(0),
						(Element) elements.get(1), (Element) elements.get(2)));
				return token;
			} else if (parameterNumber == 4) {
				currentIndex++;
				token = new Token(TokenType.FOR, new ForLoopNode((ElementVariable) elements.get(0),
						(Element) elements.get(1), (Element) elements.get(2), (Element) elements.get(3)));
				return token;
			}
		}
		
		throw new SmartScriptingLexerException("Tag not closed correctly.");
	}
	
	/**
	 * Method that produces end token.
	 * 
	 * @return Produced token.
	 * @throws SmartScriptingLexerException if token is invalid.
	 */
	private Token endNode() {
		char character = getNextChar();
		skipBlanks();
		character = getNextChar();
		
		if (character == '$') {
			character = getNextChar();
			
			if (character == '}') {
				currentIndex++;
				token = new Token(TokenType.END, null);
				return token;
			}
		}
		
		throw new SmartScriptingLexerException("Wrong end token.");
	}

	/**
	 * Method that creates operator element.
	 * 
	 * @param character Operator
	 * @return ElementOperator
	 */
	private Element getNewOperator(char character) {
		char operator = character;
		character = getNextChar();
		
		if (operator == '-' && Character.isDigit(character)) {
				currentIndex--;
				return getNewDigit(operator);
		}
		
		currentIndex--;
		return new ElementOperator(Character.toString(operator));	
	}

	/**
	 * Method that creates string element.
	 * 
	 * @return ElementString
	 * @throws SmartScriptingLexerException if string is invalid.
	 */
	private Element getNewString() {
		StringBuilder element = new StringBuilder();
		char character = getNextChar();
		
		while (character != '"') {
			if (character == '\\') {
				character = getNextChar();
				
				if (character == '\\' || character == '"') {
					element.append(character);
				} else if (character == 'n') {
					element.append('\n');
				} else if (character == 'r') {
					element.append('\r');
				} else if (character == 't') {
					element.append('\t');
				} else {
					throw new SmartScriptingLexerException("Wrong character after \\.");
				}
			} else {
				element.append(character);
			}
			
			character = getNextChar();
		}
		
		return new ElementString(element.toString());
	}

	/**
	 * Method that creates function element.
	 * 
	 * @return ElementFunction
	 * @throws SmartScriptingLexerException if function is invalid.
	 */
	private Element getNewFunction() {
		StringBuilder element = new StringBuilder();
		char character = getNextChar();
		
		if (!Character.isLetter(character)) {
			throw new SmartScriptingLexerException("First character must be letter.");
		}
		
		element.append(character);
		character = getNextChar();
		
		while (Character.isDigit(character) || Character.isLetter(character) || character == '_') {
			element.append(character);
			character = getNextChar();
		}
		
		currentIndex--;
		
		return new ElementFunction(element.toString());
	}
	/**
	 * Method that creates Variable element.
	 * 
	 * @param character First character of variable.
	 * @return ElementVariable
	 */
	private Element getNewVariable(char character) {
		StringBuilder element = new StringBuilder();
		element.append(character);
		character = getNextChar();
		
		while (Character.isDigit(character) || Character.isLetter(character) || character == '_') {
			element.append(character);
			character = getNextChar();
		}
		
		currentIndex--;
		
		return new ElementVariable(element.toString());
	}

	/**
	 * Method that creates number element.
	 * 
	 * @param character First digit of number.
	 * @return Element number
	 * @throws SmartScriptingLexerException if invalid number
	 */
	private Element getNewDigit(char character) {
		StringBuilder element = new StringBuilder();
		element.append(character);
		boolean isDouble = false;
		character = getNextChar();
		
		while ((character != ' ' && character != '\t' && character != '\r' && character != '\n')) {
			if (Character.isDigit(character)) {
				element.append(character);
			} else if (character == '.') {
				if (!isDouble) {
					element.append(character);
					isDouble = true;
				} else {
					throw new SmartScriptingLexerException("Number can't have two dots.");
				}
			} else break;
			
			character = getNextChar();
		}
		
		currentIndex--;
		
		try {
			if (isDouble) {
				return new ElementConstantDouble(Double.parseDouble(element.toString()));
			}
			return new ElementConstantInteger(Integer.parseInt(element.toString()));
		} catch (NumberFormatException e) {
			throw new SmartScriptingLexerException("Can't parse number.", e);
		}
	}

	/**
	 * Method that checks which state to use.
	 * 
	 * @param character First character of word to check.
	 * @return Created string.
	 */
	private String checkKeyWord(char character) {
		skipBlanks();
		character = getNextChar();
		String tag; 
		
		if (character == '=') {
			setState(LexerState.ECHOEQUALSTAG);
			return Character.toString(character);
		} else if (Character.isLetter(character)) {
			tag = getNewVariable(character).asText();
			
			if (tag.toUpperCase().equals("FOR")) {
				setState(LexerState.FORTAG);
			} else if (tag.toUpperCase().equals("END")) {
				setState(LexerState.ENDTAG);
			} else {
				setState(LexerState.ECHOVARIABLETAG);
			}
			
			return tag;
		}
		
		throw new SmartScriptingLexerException("Invalid data passed.");		
	}
	
	/**
	 * Method that fetches next character of document.
	 * 
	 * @return next character of document.
	 */
	private char getNextChar() {
		currentIndex++;
		
		if (currentIndex < data.length) {
			return data[currentIndex];	
		}
		
		throw new SmartScriptingLexerException("Can't get character at specified index.");
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
			currentIndex--;
			break;
		}
	}
	
}