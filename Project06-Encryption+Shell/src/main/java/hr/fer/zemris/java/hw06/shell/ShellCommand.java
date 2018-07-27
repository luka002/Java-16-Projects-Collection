package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that every shell command should implement.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * Executes command with specified behavior for that command.
	 * 
	 * @param env Environment.
	 * @param arguments Arguments.
	 * @return continue to continue, terminate to end.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns command name.
	 * 
	 * @return Command name.
	 */
	String getCommandName();

	/**
	 * Returns command description.
	 * 
	 * @return Command description.
	 */
	List<String> getCommandDescription();

}
