package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent help command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Prints description of selected command or all of them in none is selected.
	 * 
	 * @param env environment
     * @param arguments Provided arguments
     * @return ShellStatus.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, ShellCommand> map = env.commands();
		
		if (!arguments.isEmpty()) {
			ShellCommand command = map.get(arguments);
			
			if (command == null) {
				env.writeln("Wrong argument.");
				return ShellStatus.CONTINUE;
			}
			printCommandDescription(env, command);
			
		} else {
			map.forEach((string, command) -> printCommandDescription(env, command));
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints command description.
	 * 
	 * @param env Environment.
	 * @param command Command.
	 */
	private void printCommandDescription(Environment env, ShellCommand command) {
		List<String> description = command.getCommandDescription();
		
		env.write(command.getCommandName());
		description.forEach(s -> System.out.println(s));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("        Prints description of selected command");
		description.add("            or all of them in none is selected.");

		return Collections.unmodifiableList(description);
	}

}
