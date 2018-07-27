package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents symbol command. There are 3 symbols: PROMPT -
 * which is prompt symbol, MULTILINE - which replaces prompt when input spans
 * trough multiple lines and MORELINES - which enables multiline input if it is
 * last character in line.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Expects Accepts 1 or 2 arguments. First argument can be:
	 *    PROMPT - writes out current prompt symbol.
     *    MORELINES - writes out current morelines symbol.
     *    MULTILINE - writes out current multiline symbol.
     * If Second argument is present it replaces specified symbol.
     * 
     * @param env environment
     * @param arguments Provided arguments
     * @return ShellStatus.CONTUNUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] splitArguments = arguments.split("\\s+");
		boolean validArguments = true;
		
		if (splitArguments.length == 1) {
			switch (splitArguments[0]) {
				case "PROMPT":
					print(env, "PROMPT", env.getPromptSymbol());
					break;
				case "MORELINES":
					print(env, "MORELINES", env.getMorelinesSymbol());
					break;
				case "MULTILINE":
					print(env, "MULTILINE", env.getMultilineSymbol());
					break;
				default:
					validArguments = false;
			}
		} else if (splitArguments.length == 2 && splitArguments[1].length() == 1) {
			switch (splitArguments[0]) {
				case "PROMPT":
					print(env, "PROMPT", env.getPromptSymbol(), splitArguments[1]);
					env.setPromptSymbol(Character.valueOf(splitArguments[1].charAt(0)));
					break;
				case "MORELINES":
					print(env, "MORELINES", env.getMorelinesSymbol(), splitArguments[1]);
					env.setMorelinesSymbol(Character.valueOf(splitArguments[1].charAt(0)));
					break;
				case "MULTILINE":
					print(env, "MULTILINE", env.getMultilineSymbol(), splitArguments[1]);
					env.setMultilineSymbol(Character.valueOf(splitArguments[1].charAt(0)));
					break;
				default:
					validArguments = false;
			}
		} else { 
			validArguments = false;
		}
		
		if (!validArguments) {
			System.out.println("Invalid arguments.");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints out character that represents symbol.
	 * 
	 * @param env Environment
	 * @param string Symbol.
	 * @param promptSymbol Character.
	 */
	private void print(Environment env, String string, Character promptSymbol) {
		env.writeln("Symbol for " + string + " is '" + promptSymbol + "'");
	}
	
	/**
	 * Prints out previous and current symbols character after change.
	 * 
	 * @param env Environment.
	 * @param string Symbol.
	 * @param promptSymbol Previous character.
	 * @param symbol Current character.
	 */
	private void print(Environment env, String string, Character promptSymbol, String symbol) {
		env.writeln("Symbol for " + string + " changed from '" + promptSymbol + "' to '" + symbol + "'");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("      Accepts 1 or 2 arguments. First argument can be:");
		description.add("            	PROMPT - writes out current prompt symbol.");
		description.add("            	MORELINES - writes out current morelines symbol.");
		description.add("            	MULTILINE - writes out current multiline symbol.");
		description.add("            Second argument replaces specified symbol.");
		
		return Collections.unmodifiableList(description);
	}

}
