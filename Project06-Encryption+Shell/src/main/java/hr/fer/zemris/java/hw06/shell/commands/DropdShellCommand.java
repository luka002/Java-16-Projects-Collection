package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent dropd command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * Removes path from top of the stack.
	 * 
	 * @param env environment
	 * @param arguments Provided arguments
	 * @return ShellStatus.CONTUNUE
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("This command does not accept any argument.");
			return ShellStatus.CONTINUE;
		}
	
		try {
			((Stack<Path>)env.getSharedData("cdstack")).pop();
		} catch (EmptyStackException | NullPointerException e) {
			env.writeln("Stack is empty.");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "dropd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("       Removes path from top of the stack.");

		return Collections.unmodifiableList(description);
	}

}
