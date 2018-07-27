package hr.fer.zemris.java.hw04.db;

/**
 * Strategy for comparing strings.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * @param value1 First value.
	 * @param value2 Second value.
	 * @return true if condition is met, else false.
	 */
	public boolean satisfied(String value1, String value2);
	
}
