package hr.fer.zemris.java.hw04.db;

/**
 * Concrete strategie for each comparison operator.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ComparisonOperators {

	/**
	 * Strategy that checks if first string is less than second string.
	 */
	public static final IComparisonOperator LESS = (string1, string2) -> {
		if (string1.compareTo(string2) < 0) {
			return true;
		}
		
		return false;
	};
	
	/**
	 * Strategy that checks if first string is less or equal than second string.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (string1, string2) -> {
		if (string1.compareTo(string2) <= 0) {
			return true;
		}
		
		return false;
	};
	
	/**
	 * Strategy that checks if first string is greater than second string.
	 */
	public static final IComparisonOperator GREATER = (string1, string2) -> {
		if (string1.compareTo(string2) > 0) {
			return true;
		}
		
		return false;
	};
	
	/**
	 * Strategy that checks if first string is greater or equals than second string.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (string1, string2) -> {
		if (string1.compareTo(string2) >= 0) {
			return true;
		}
		
		return false;
	};
	
	/**
	 * Strategy that checks if first string is equal to second string.
	 */
	public static final IComparisonOperator EQUALS = (string1, string2) -> {
		if (string1.compareTo(string2) == 0) {
			return true;
		}
		
		return false;
	};
	
	/**
	 * Strategy that checks if first string is not equal to second string.
	 */
	public static final IComparisonOperator NOT_EQUALS = (string1, string2) -> {
		if (string1.compareTo(string2) != 0) {
			return true;
		}
		
		return false;
	};
	
	/**
	 * Strategy that checks if first string is equal to second string. In second string 
	 * can be one wildcard(*) that replaces every character.
	 */
	public static final IComparisonOperator LIKE = (string1, string2) -> {
		StringBuilder builder = new StringBuilder(string2);
		int wildCardPosition = -1;
		boolean wildCardFound = false;
		
		for (int i = 0; i < builder.length(); i++) {
			if (builder.charAt(i) == '*') {
				if (wildCardFound == true) throw new IllegalArgumentException("There can only be one wildcard.");
				
				wildCardPosition = i;
				wildCardFound = true;
			}
		}
		
		if (!wildCardFound) return string1.compareTo(string2) == 0;
		
		if (wildCardPosition == 0) {
				return string1.matches(".*" + string2.substring(1, string2.length()));
				
		} else if (wildCardPosition == (string2.length()-1)) {
				return string1.matches(string2.substring(0, wildCardPosition) + ".*");
		
		} else {
			return string1.matches(string2.substring(0, wildCardPosition) + ".*" + string2.substring(wildCardPosition+1, string2.length()));	
		}
	};
	
}
