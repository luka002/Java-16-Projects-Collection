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
 * Class that represent tree command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Class designed to go recursively trough file tree.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class MyFileVisitor extends SimpleFileVisitor<Path> {
		/** Indentation */
		private StringBuilder builder;
		/** Environment */
		private Environment env;

		/**
		 * Constructor.
		 * 
		 * @param env Environment.
		 */
		public MyFileVisitor(Environment env) {
			this.builder = new StringBuilder();
			this.env = env;
		}

		/**
		 * Executes before visiting directory. Writes out directory name
		 * that will be visited and adds indentation.
		 * 
		 * @param path Path.
		 * @param attribs File attributes.
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attribs) throws IOException {
			env.writeln(builder.toString() + path.getFileName());
			builder.append("  ");
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Executes after visiting directory. Subtracts indentation.
		 * 
		 * @param path Path.
		 * @param ex Exception
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException ex) throws IOException {
			builder.delete(builder.length() - 2, builder.length());
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Executes when visiting file. Writes out file name.
		 * 
		 * @param path Path.
		 * @param attribs File attributes.
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) throws IOException {
			env.writeln(builder.toString() + path.getFileName());
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * expects a single argument: directory name and prints a 
	 * tree (each directory level shifts output two charatcers to the right).
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
			env.writeln("Only 1 argument expected.");
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));
		
		if (!path.isAbsolute()) {	
			path = env.getCurrentDirectory().resolve(path);
		} 

		if (!Files.isDirectory(path)) {
			env.writeln("Specified path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(path, new MyFileVisitor(env));
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
		return "tree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("        Expects a single argument: directory name and prints a tree.");

		return Collections.unmodifiableList(description);
	}

}
