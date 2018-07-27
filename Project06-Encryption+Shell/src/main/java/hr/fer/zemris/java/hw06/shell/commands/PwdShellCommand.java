package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent pwd command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * Prints out current absolute default shell directory.
	 * 
	 * @param env environment
     * @param arguments Provided arguments
     * @return ShellStatus.CONTUNUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("This command does not accept any argument.");
		} else {
			env.writeln(env.getCurrentDirectory().toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pwd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("         Prints out current absolute default shell directory.");

		return Collections.unmodifiableList(description);

	}

}
