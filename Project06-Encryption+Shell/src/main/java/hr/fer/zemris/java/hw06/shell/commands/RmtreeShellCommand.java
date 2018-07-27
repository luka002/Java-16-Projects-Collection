package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParseArguments;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent rmtree command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class RmtreeShellCommand implements ShellCommand {
	
	/**
	 * Class designed to go recursively trough file tree and
	 * delete all its content.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class RmtreeFileVisitor extends SimpleFileVisitor<Path> {
	
		/**
		 * Executes after visiting directory. Deletes the directory.
		 * 
		 * @param path Path.
		 * @param ex Exception
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException ex) throws IOException {
			Files.delete(path);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Executes when visiting file. Deletes the file.
		 * 
		 * @param path Path.
		 * @param attribs File attributes.
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) throws IOException {
			Files.delete(path);
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Removes directory and all its content.
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
		
		Path path = Paths.get(list.get(0));
		
		if (path.isAbsolute()) {
			env.writeln("Accepts only relative path.");
			return ShellStatus.CONTINUE;
		}
		
		path = env.getCurrentDirectory().resolve(path);
		
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			env.writeln("Given path is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(path, new RmtreeFileVisitor());
		} catch (IOException e) {
			env.writeln("Error.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "rmtree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("      Removes directory and all its content.");

		return Collections.unmodifiableList(description);
	}

}
