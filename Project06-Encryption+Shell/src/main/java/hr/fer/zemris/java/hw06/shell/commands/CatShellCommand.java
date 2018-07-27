package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
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
 * Class that represent cat command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Opens given file and writes its content to console. Accepts 1 or 2 arguments. 
	 * First argument represents path and second argument represents charset for 
	 * decoding a file. If charset is not provided, default one will be used.
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

		if (list.size() == 2 || list.size() == 1) {
			Charset charset = null;

			if (list.size() == 2) {
				try {
					charset = Charset.forName(list.get(1));
				} catch (Exception e) {
					env.writeln("Wrong charset provided");
					return ShellStatus.CONTINUE;
				}
			} else {
				charset = Charset.defaultCharset();
			}

			Path path = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));
			
			try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
				String line = null;

				while ((line = reader.readLine()) != null) {
					env.writeln(line);
				}
			} catch (IOException e) {
				env.writeln("Error with given path.");
			}

		} else {
			env.writeln("Expected number of arguments is 1 or 2.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("         Opens given file and writes its content to console.");
		description.add("            Accepts 1 or 2 arguments. First argument represents path and");
		description.add("            second argument represents charset for decoding a file.");
		description.add("            If charset is not provided, default one will be used.");

		return Collections.unmodifiableList(description);
	}

}
