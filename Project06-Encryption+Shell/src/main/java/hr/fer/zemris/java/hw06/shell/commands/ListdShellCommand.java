package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent listd command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * Writes out all paths on the stack.
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
		
		Stack<Path> stack;
		
		try {
			stack = ((Stack<Path>)env.getSharedData("cdstack"));
		} catch (NullPointerException e) {
			env.writeln("No stored directories.");
			return ShellStatus.CONTINUE;
		}
		
		if (stack == null || stack.isEmpty()) {
			env.writeln("No stored directories.");
		} else {
			Path[] paths = new Path[stack.size()];
			stack.toArray(paths);
			
			for (int i = paths.length-1; i >= 0; i--) {
				env.writeln(paths[i].toString());
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "listd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("       Writes out all paths on the stack.");

		return Collections.unmodifiableList(description);
	}

}
