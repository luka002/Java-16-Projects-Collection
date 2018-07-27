package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents a parser of query statement.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class QueryParser {
	/** Provided data */
	private char[] data;
	/** Current index */
	private int currentIndex;
	/** List of queries */
	private List<ConditionalExpression> queries;

	/**
	 * Constructor.
	 * 
	 * @param string Provided data.
	 */
	public QueryParser(String string) {
		this.data = string.toCharArray();
		this.currentIndex = 0;
		this.queries = new ArrayList<>();
		parse();
	}

	/**
	 * Checks if it is direct query.
	 * 
	 * @return true if query is direct, else false.
	 */
	public boolean isDirectQuery() {
		if (queries.size() == 1 && queries.get(0).getFieldValue() == FieldValueGetters.JMBAG 
				&& queries.get(0).getComparisonOperator() == ComparisonOperators.EQUALS) {
			return true;
		}
		
		return false;
	}

	/**
	 * @return Jmbag if query is direct.
	 * @throws IllegalStateException if not direct query.
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return queries.get(0).getStringLiteral();
		}
		
		throw new IllegalStateException("Can not querie jmbag for non direct querie.");
	}
	
	/**
	 * @return List of queries.
	 */
	public List<ConditionalExpression> getQuery() {
		return queries;
	}

	/**
	 * Method that parses data into queries.
	 */
	private void parse() {
		skipBlanks();

		while (currentIndex < data.length) {
			IFieldValueGetter fieldValue = getFieldValue();
			IComparisonOperator comparisonOperator = getComparisonOperator();
			String stringLiteral = getStringLiteral();

			ConditionalExpression expression = new ConditionalExpression(fieldValue, stringLiteral, comparisonOperator);
			queries.add(expression);

			if (isLastExpression())
				break;

			checkForAND();
		}
	}

	/**
	 * Method that checks if fieldValue is correct.
	 * 
	 * @return Correct FieldValueGetter.
	 * @throws IllegalArgumentException if fieldValue is invalid.
	 */
	private IFieldValueGetter getFieldValue() {
		StringBuilder fieldValue = new StringBuilder();
		char c = getNextChar();
		
		while (Character.isLetter(c) && c != 'L') {
			fieldValue.append(c);
			c = getNextChar();
		}
		
		switch (fieldValue.toString()) {
			case "jmbag":
				return FieldValueGetters.JMBAG;
			case "lastName":
				return FieldValueGetters.LAST_NAME;
			case "firstName":
				return FieldValueGetters.FIRST_NAME;
		}
		
		throw new IllegalArgumentException("Field value not provided correctly.");
	}

	/**
	 * Method that checks if comparison operator is correct.
	 * 
	 * @return Correct ComparisonOperator.
	 * @throws IllegalArgumentException if comparison operator is invalid.
	 */
	private IComparisonOperator getComparisonOperator() {
		skipBlanks();
		
		StringBuilder operator = new StringBuilder();
		char c = getNextChar();
		
		while (c != '"') {
			operator.append(c);
			c = getNextChar();
		}
		
		switch (operator.toString().trim()) {
			case "LIKE":
				return ComparisonOperators.LIKE;
			case ">":
				return ComparisonOperators.GREATER;
			case "<":
				return ComparisonOperators.LESS;
			case ">=":
				return ComparisonOperators.GREATER_OR_EQUALS;
			case "<=":
				return ComparisonOperators.LESS_OR_EQUALS;
			case "=":
				return ComparisonOperators.EQUALS;
			case "!=":
				return ComparisonOperators.NOT_EQUALS;
		}
		
		throw new IllegalArgumentException("Incorrect operator.");
	}

	/**
	 * Method that checks if string literal is correct.
	 * 
	 * @return Correct string literal.
	 * @throws IllegalArgumentException if string literal is invalid.
	 */
	private String getStringLiteral() {
		StringBuilder stringLiteral = new StringBuilder();
		char c = getNextChar();
		
		while (c != '"') {
			stringLiteral.append(c);
			c = getNextChar();
		}
		
		return stringLiteral.toString();
	}

	/**
	 * Method that checks if it is last expression in data.
	 * 
	 * @return true if it is last expression, else false.
	 */
	private boolean isLastExpression() {
		if (endOfFile())
			return true;

		char c = getNextChar();

		if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
			throw new IllegalArgumentException("Before AND comes space.");
		}

		skipBlanks();

		if (endOfFile())
			return true;

		return false;
	}

	/**
	 * Is it end of data.
	 * 
	 * @return true if end of data, else false.
	 */
	private boolean endOfFile() {
		currentIndex++;

		if (currentIndex >= data.length)
			return true;

		currentIndex--;
		return false;
	}

	/**
	 * Checks for "AND" keyword.
	 */
	private void checkForAND() {
		char c = getNextChar();

		if (c == 'a' || c == 'A') {
			c = getNextChar();

			if (c == 'n' || c == 'N') {
				c = getNextChar();

				if (c == 'd' || c == 'D') {
					c = getNextChar();

					if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
						skipBlanks();
						return;
					}
				}
			}
		}

		throw new IllegalArgumentException("After expresion comes \"AND\" and after \"AND\" comes space.");
	}

	/**
	 * Gets next character from data.
	 * 
	 * @return Next character from data.
	 * @throws IndexOutOfBoundsException if index is out of bounds.
	 */
	private char getNextChar() {
		currentIndex++;

		if (currentIndex < data.length) {
			return data[currentIndex];
		}

		throw new IndexOutOfBoundsException("Can't get character at specified index.");
	}

	/**
	 * Skips blanks in data.
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
