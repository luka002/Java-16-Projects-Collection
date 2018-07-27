package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParseArguments;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent pushd command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Pushed current default directory on stack and sets path provided as argument
	 * as new default directory.
	 * 
	 * @param env environment
	 * @param arguments Provided arguments
	 * @return ShellStatus.CONTUNUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list = null;

		try {
			list = ParseArguments.parse(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (list.size() != 1) {
			env.writeln("Expected 1 argument.");
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));

		if (!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("Given path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		env.setSharedData("cdstack", env.getCurrentDirectory());
		env.setCurrentDirectory(path);

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pushd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("       Pushed current default directory on stack and sets path");
		description.add("            provided as argument as new default directory.");

		return Collections.unmodifiableList(description);
	}

}
