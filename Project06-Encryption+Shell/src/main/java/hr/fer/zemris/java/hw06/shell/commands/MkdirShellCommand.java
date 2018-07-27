package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
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
 * Class that represent mkdir command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand{

	/**
	 * Takes a single argument: directory name, and 
	 * creates the appropriate directory structure.
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
		String root;
		
		try {
			root = path.getRoot().toString();
		} catch (Exception e) {
			root = "";
		}
		
		try {
			for (int i = 1; i <= path.getNameCount(); i++) {
				Path newPath = Paths.get(root + path.subpath(0, i).toString());
				
				if (!Files.isDirectory(newPath)) {
					Files.createDirectory(newPath);
				}

			}
		} catch (IOException e) {
			env.writeln("Could not make specified directory.");
		} 
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("       takes a single argument: directory name, and");
		description.add("            creates the appropriate directory structure.");

		return Collections.unmodifiableList(description);
	}

	
}
