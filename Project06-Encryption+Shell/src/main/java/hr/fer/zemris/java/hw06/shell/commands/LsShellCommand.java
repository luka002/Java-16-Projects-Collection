package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParseArguments;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represent ls command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Takes a single argument – directory – and writes a directory listing.
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
			env.writeln("1 argument expected.");
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));

		if (!Files.isDirectory(path)) {
			env.writeln("Specified path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path entry : stream) {
				
				BasicFileAttributeView faView = Files.getFileAttributeView(entry, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				String formattedDateTime = getFormattedDateTime(attributes);
				
				StringBuilder builder = new StringBuilder();
				builder.append(Files.isDirectory(entry) ? 'd' : '-');
				builder.append(Files.isReadable(entry) ? 'r' : '-');
				builder.append(Files.isWritable(entry) ? 'w' : '-');
				builder.append(Files.isExecutable(entry) ? 'x' : '-');
				
				String size = String.format("%11d", Files.size(entry));
				
				builder.append(size);
				builder.append(" " + formattedDateTime + " ");
				builder.append(entry.getFileName());
				
				env.writeln(builder.toString());
			}
		} catch (IOException e) {
			env.writeln("Error.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Formats date and time of file creation.
	 * 
	 * @param attributes File attributes.
	 * @return formated string.
	 */
	private String getFormattedDateTime(BasicFileAttributes attributes) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileTime fileTime = attributes.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("          Takes a single argument – directory – and writes a directory listing.");

		return Collections.unmodifiableList(description);
	}

}
