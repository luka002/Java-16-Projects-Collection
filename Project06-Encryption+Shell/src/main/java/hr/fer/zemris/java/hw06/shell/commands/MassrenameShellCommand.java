package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ParseArguments;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw08.shell.lexer.LexerException;
import hr.fer.zemris.java.hw08.shell.name.ConcreteNameBuilderInfo;
import hr.fer.zemris.java.hw08.shell.name.MainNameBuilder;
import hr.fer.zemris.java.hw08.shell.name.NameBuilderInfo;
import hr.fer.zemris.java.hw08.shell.name.NameBuilderParser;

/**
 * Class that represent massrename command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * Renames all files from source path and moves them to
	 * destination path. Has four subcommands. It receives 4 or 5
	 * argument depending on the subcommand. Subcommands are:
	 *   filter: has 4 arguments(DIR1 DIR2 filter MASK) and filters
	 *     files from DIR1 based on given MASK and writes them out.
	 *   groups: has 4 arguments(DIR1 DIR2 groups MASK) and writes
	 *     out files from DIR1 and their groups.
	 *   show: has 5 arguments(DIR1 DIR2 show MASK EXPRESSION) and
	 *     writes out renamed files from DIR1 based on MASK and EXPRESSION.
	 *   execute: has 5 arguments(DIR1 DIR2 execute MASK EXPRESSION) and
	 *     executes moving and renaming files from DIR1 to DIR2.
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
		
		Path source = env.getCurrentDirectory().resolve(Paths.get(list.get(0)));
		Path destination = env.getCurrentDirectory().resolve(Paths.get(list.get(1)));
		
		if (!Files.exists(source) || !Files.isDirectory(source)) {
			env.writeln("Source is not an existing directory.");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.exists(destination) || !Files.isDirectory(destination)) {
			env.writeln("Destination is not an existing directory.");
			return ShellStatus.CONTINUE;
		}
		
		switch (list.get(2)) {
			case "filter":
				if (list.size() == 4) {
					writeMatchingFiles(env, source, list.get(3), false);
				} else {
					env.writeln("Wrong number of arguments.");
				}
				break;
				
			case "groups":
				if (list.size() == 4) {
					writeMatchingFiles(env, source, list.get(3), true);
				} else {
					env.writeln("Wrong number of arguments.");
				}
				break;
				
			case "show":
				if (list.size() == 5) {
					renameFiles(env, source, destination, list.get(3), list.get(4), false);
				} else {
					env.writeln("Wrong number of arguments.");
				}
				break;
				
			case "execute":
				if (list.size() == 5) {
					renameFiles(env, source, destination, list.get(3), list.get(4), true);
				} else {
					env.writeln("Wrong number of arguments.");
				}
				break;
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Moves renamed files from source path to the destination path.
	 * Filtering is done based on pattern and renaming based on expression.
	 * 
	 * @param env Environment.
	 * @param source Source path.
	 * @param destination Destination path.
	 * @param pattern Pattern for filtering.
	 * @param expression Expression for renaming.
	 * @param rename if true renaming will be executed, if false
	 * only preview of renaming will be shown.
	 */
	private void renameFiles(Environment env, Path source, Path destination,
							String pattern, String expression, boolean rename) {
		NameBuilderParser parser = null;
		
		try {
			parser = new NameBuilderParser(expression);
		} catch (LexerException e) {
			env.writeln("Wrong expression.");
			return;
		}
		
		MainNameBuilder builder = parser.getNameBuilder();
		Pattern myPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {	
			for (Path entry : stream) {
				Matcher matcher = myPattern.matcher(entry.getFileName().toString());
				
				if (matcher.matches() && Files.exists(entry) && !Files.isDirectory(entry)) {
					NameBuilderInfo nameBuilderInfo = new ConcreteNameBuilderInfo(matcher);
					builder.execute(nameBuilderInfo);
					String newName = builder.getStringBuilder().toString();
					
					if (rename) {
						try {
							Files.move(entry, destination.resolve(Paths.get(newName)));
						} catch (Exception e) {
							env.writeln("Error.");
						}
					} else {
						env.writeln(newName);
					}
				}
			}
		} catch (IOException e) {
			env.writeln("Error.");
		}
	}

	/**
	 * Writes out files from source path that satisfy pattern and all groups
	 * if writeAllGroups is true.
	 * 
	 * @param env Environment.
	 * @param source Source path.
	 * @param pattern Pattern.
	 * @param writeAllGroups Flag for writing all groups.
	 */
	private void writeMatchingFiles(Environment env, Path source, String pattern, boolean writeAllGroups) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
			Pattern myPatter = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
			
			for (Path entry : stream) {
				Matcher matcher = myPatter.matcher(entry.getFileName().toString());
				
				if (matcher.matches() && Files.exists(entry) && !Files.isDirectory(entry)) {
					env.write(matcher.group(0) + " ");
					
					if (writeAllGroups) {
						for (int i = 0; i <= matcher.groupCount(); i++) {
							env.write(i + ": " + matcher.group(i) + " ");
						}
					}
					
					env.write("\n");
				}
			}
		} catch (IOException e) {
			env.writeln("Error.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "massrename";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		
		description.add("  Renames all files from source path and moves them to");
		description.add("            destination path. Has four subcommands. It receives 4 or 5");
		description.add("            argument depending on the subcommand. Subcommands are:");
		description.add("              filter: has 4 arguments(DIR1 DIR2 filter MASK) and filters");
		description.add("                files from DIR1 based on given MASK and writes them out.");
		description.add("              groups: has 4 arguments(DIR1 DIR2 groups MASK) and writes");
		description.add("                out files from DIR1 and their groups.");
		description.add("              show: has 5 arguments(DIR1 DIR2 show MASK EXPRESSION) and");
		description.add("                writes out renamed files from DIR1 based on MASK and EXPRESSION.");
		description.add("              execute: has 5 arguments(DIR1 DIR2 execute MASK EXPRESSION) and");
		description.add("                executes moving and renaming files from DIR1 to DIR2.");

		return Collections.unmodifiableList(description);
	}

}
