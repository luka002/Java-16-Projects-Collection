package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent exit command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Terminates shell.
	 * 
	 * @param env environment
     * @param arguments Provided arguments
     * @return ShellStatus.TERMINATE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "exit";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("        Terminates shell.");

		return Collections.unmodifiableList(description);
	}

}
