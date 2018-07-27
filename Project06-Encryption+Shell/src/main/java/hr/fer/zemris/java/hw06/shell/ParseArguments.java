package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for parsing arguments for commands.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ParseArguments {

	/**
	 * Splits arguments on empty spaces except if space is
	 * between quotes.
	 * 
	 * @param arguments Arguments.
	 * @return List of arguments.
	 * @throws IllegalArgumentException if before or after quotes
	 * is not space.
	 */
	public static List<String> parse(String arguments) {
		List<String> list = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		boolean inQuotes = false;

		for (int i = 0; i < arguments.length(); i++) {
			char c = arguments.charAt(i);

			if (inQuotes) {
				if (c == '\"') {
					if (i + 1 < arguments.length() && !Character.isWhitespace(arguments.charAt(i + 1))) {
						throw new IllegalArgumentException("After ending '\"' has to come space or nothing.");
					}
					list.add(builder.toString());
					builder = new StringBuilder();
					inQuotes = false;

				} else if (c == '\\' && (i + 1 < arguments.length()) && (list.size()<3)) {
					char nextC = arguments.charAt(++i);

					if (nextC == '\"' || nextC == '\\') {
						builder.append(nextC);
					} else {
						builder.append(c);
						builder.append(nextC);
					}
					
				} else if (i+1 == arguments.length()) {
					throw new IllegalArgumentException("Quotes never closed.");
				
				} else {
					builder.append(c);
				}

			} else {
				if (c == '\"') {
					if (i+1 == arguments.length()) {
						throw new IllegalArgumentException("Quotes never closed.");
					
					} else if (i != 0 && !Character.isWhitespace(arguments.charAt(i-1))) {
						throw new IllegalArgumentException("Before starting '\"' has to come space or nothing.");
					
					} else if (builder.length() != 0) {
						list.add(builder.toString());
						builder = new StringBuilder();
					}
					inQuotes = true;
					
				} else if (!Character.isWhitespace(c)) {
					builder.append(c);
					
					if (i+1 == arguments.length()) {
						list.add(builder.toString());
					}
					
				} else if (builder.length() != 0) {
					list.add(builder.toString());
					builder = new StringBuilder();
				}
			}
		}

		return list;
	}

}
