package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
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
 * Class that represent popd command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * Removes path from top of the stack and sets it as default
	 * current shell directory if it exists.
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
		
		Path path;
		
		try {
			path = ((Stack<Path>)env.getSharedData("cdstack")).pop();
		} catch (EmptyStackException | NullPointerException e) {
			env.writeln("Stack is empty.");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.exists(path)) {
			env.writeln("Popped path had been removed.");
		} else {
			env.setCurrentDirectory(path);
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "popd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("        Removes path from top of the stack and sets it as default");
		description.add("            current shell directory if it exists.");

		return Collections.unmodifiableList(description);
	}

}
