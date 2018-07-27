package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParseArguments;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent copy command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Expects two arguments: source file name and destination file name. 
	 * If destination file exists, user is asked if he wants to overwrite it.
	 * Command works only with files (no directories). If the second argument 
	 * is directory, it is assumed that user wants to copy the original file 
	 * into that directory using the original file name.
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

		if (list.size() != 2) {
			env.writeln("Expected 2 arguments.");
			return ShellStatus.CONTINUE;
		}

		Path source = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));
		Path destination = env.getCurrentDirectory().resolve(Paths.get(list.get(1)));		

		if (!Files.exists(source) || Files.isDirectory(source)) {
			env.writeln("Source file does not exist.");
			
		} else if (Files.exists(destination) && !Files.isDirectory(destination)) {
			env.writeln("Destination file exists. If you want to override it type 'Y'.");
			String input = env.readLine();

			if (input.equalsIgnoreCase("Y")) {
				env.writeln("Copying accepted.");
				copy(source, destination, env);
			} else {
				env.writeln("Copying declined.");
			}
			
		} else if (Files.isDirectory(destination)) {
			copy(source, Paths.get(destination.toString() + "/" + source.getFileName()), env);
			
		} else {
			copy(source, destination, env);
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies file from source to destination.
	 * 
	 * @param source Source path.
	 * @param destination Destination path.
	 * @param env Environment.
	 */
	private void copy(Path source, Path destination, Environment env) {
		try (InputStream input = Files.newInputStream(source, StandardOpenOption.READ);
				OutputStream output = Files.newOutputStream(destination, StandardOpenOption.CREATE)) {
			byte[] buff = new byte[1024];
			
			while (true) {
				int r = input.read(buff);
				if (r < 1)
					break;
				output.write(buff, 0, r);
			}
		} catch (IOException ex) {
			env.writeln("Error.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("        Expects two arguments: source file name and destination file name.");
		description.add("            If destination file exists, user is asked if he wants to overwrite it.");
		description.add("            Command works only with files (no directories). If the second argument");
		description.add("            is directory, it is assumed that user wants to copy the original file.");
		description.add("            into that directory using the original file name.");

		return Collections.unmodifiableList(description);
	}

}
