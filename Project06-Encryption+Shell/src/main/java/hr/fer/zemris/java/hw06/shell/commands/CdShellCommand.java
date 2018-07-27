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
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent cd command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * Receives one argument - path - that can be absolute or relative
	 * path at writes it as default shell path in the environment.
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

		Path path = Paths.get(list.get(0));
		
		if (Files.isDirectory(path) && path.isAbsolute()) {
			env.setCurrentDirectory(path);
		} else {
			try {
				env.setCurrentDirectory(env.getCurrentDirectory().resolve(path));
			} catch (ShellIOException e) {
				env.writeln("Wrong path provided.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("          Receives one argument - path - that can be absolute or relative"); 
		description.add("            path at writes it as default shell path in the environment.");

		return Collections.unmodifiableList(description);
	}

}
