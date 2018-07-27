package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent charsets command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Lists names of supported charsets for your Java platform.
	 * 
	 * @param env environment
     * @param arguments Provided arguments
     * @return ShellStatus.CONTUNUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			SortedMap<String, Charset> charsets = Charset.availableCharsets();

			env.writeln("Available charsets are:");
			charsets.forEach((s, c) -> env.writeln("\t" + s));
		} else {
			env.writeln("This command does not recieve arguments.");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("    Writes out supported charsets for your Java platform."); 

		return Collections.unmodifiableList(description);
	}

}
