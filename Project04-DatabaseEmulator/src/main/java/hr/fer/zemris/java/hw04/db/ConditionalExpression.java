package hr.fer.zemris.java.hw04.db;

/**
 * Class that stores one query.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ConditionalExpression {
	/** Field value */
	private IFieldValueGetter fieldValue;
	/** String literal */
	private String stringLiteral;
	/** Comparison operator */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor.
	 * 
	 * @param fieldValue Field value.
	 * @param stringLiteral String literal.
	 * @param comparisonOperator Comparison operator.
	 */
	public ConditionalExpression(IFieldValueGetter fieldValue, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldValue = fieldValue;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * @return Field value.
	 */
	public IFieldValueGetter getFieldValue() {
		return fieldValue;
	}

	/**
	 * 
	 * @return String literal.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return Comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
