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
 * Class that represent cptree command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CptreeShellCommand implements ShellCommand {

	/**
	 * Class designed to go recursively trough file tree and
	 * copy all its content.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class CptreeFileVisitor extends SimpleFileVisitor<Path> {
		/** Source path */
		private Path sourcePath;
		/** Target path */
		private Path targetPath;
		/** Flag that says if root directory should be included. */
		private boolean fullCopy;
		
		/**
		 * Initialized the object.
		 * 
		 * @param fullCopy Flag that says if root directory should be included.
		 * @param targetPath Target path.
		 */
		public CptreeFileVisitor(boolean fullCopy, Path targetPath) {
			this.sourcePath = null;
	        this.targetPath = targetPath;
	        this.fullCopy = fullCopy;
	    }

		/**
		 * Copies directory.
		 * 
		 * @param path Path.
		 * @param attrs File attributes.
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, final BasicFileAttributes attrs) throws IOException {
			if (sourcePath == null && fullCopy) {
				sourcePath = dir;
				Files.createDirectories(targetPath.resolve(dir.getFileName()));
			} else if (sourcePath == null) {
				sourcePath = dir;
			} else if (fullCopy){
				Files.createDirectories(targetPath.resolve(sourcePath.resolve(Paths.get("..")).relativize(dir)));
			} else {
				Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
			}
			
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Copies file.
		 * 
		 * @param path Path.
		 * @param attrs File attributes.
		 * @throws IOException
		 * @return FileVisitResult.CONTINUE
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (fullCopy){
				Files.copy(file, targetPath.resolve(sourcePath.resolve(Paths.get("..")).relativize(file)));
			} else {
				Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));				
			}

			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Copies directory and all its content.
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

		try {
			if (!Files.exists(source) || !Files.isDirectory(source)) {
				env.writeln("Source file does not exist or is not directory.");

			} else if (Files.exists(destination) && Files.isDirectory(destination)) {
				Files.walkFileTree(source, new CptreeFileVisitor(true, destination));

			} else if (Files.exists(destination.getParent()) && Files.isDirectory(destination.getParent())) {
				Files.walkFileTree(source, new CptreeFileVisitor(false, destination));
			
			} else {
				env.writeln("Can not copy.");
			}
		} catch (IOException e) {
			env.writeln("Error");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cptree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("      Copies directory and all its content.");

		return Collections.unmodifiableList(description);
	}

}
