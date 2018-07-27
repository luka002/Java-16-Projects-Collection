package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Command line program which supports multiple commands for
 * interaction with file system. Supported commands are: charsets, cat, 
 * ls, tree, copy, mkdir, hexdump, exit, help, symbol.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class MyShell {
	/** Scanner for user input. */
	static Scanner scanner = new Scanner(System.in);

	/**
	 * Method that first executes when program starts.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = addCommands();
		Environment environment = new MyEnvironment(commands);
		environment.writeln("Welcome to MyShell v 1.0");

		for (ShellStatus status = null; status != ShellStatus.TERMINATE;) {
			String input = "";
			environment.write(environment.getPromptSymbol() + " ");
			input = environment.readLine();
			
			while (!input.isEmpty() && input.charAt(input.length() - 1) == environment.getMorelinesSymbol().charValue()) {
				environment.write(environment.getMultilineSymbol() + " ");
				input = input.substring(0, input.length() - 1);
				input += environment.readLine();
			}

			String commandName = input.split("\\s+")[0];
			String arguments = input.substring(commandName.length()).trim();
			ShellCommand command = commands.get(commandName);
			
			if (command == null) {
				environment.writeln("Invalid command.");
				status = ShellStatus.CONTINUE;
			} else {
				status = command.executeCommand(environment, arguments);	
			}
		} 
		
		scanner.close();
	}

	/**
	 * Creates sorted map that contains command name as key and
	 * command as value.
	 * 
	 * @return Sorted map that contains command name as key and
	 * command as value.
	 */
	private static SortedMap<String, ShellCommand> addCommands() {
		SortedMap<String, ShellCommand> commands = new TreeMap<>();

		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("rmtree", new RmtreeShellCommand());
		commands.put("cptree", new CptreeShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
		
		return commands;
	}

	/**
	 * Class trough which user interacts with shell.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class MyEnvironment implements Environment {
		/** Map that contains all commands */
		private SortedMap<String, ShellCommand> commands;
		/** Prompt symbol */
		private Character promptSymbol;
		/** Moreline symbol */
		private Character morelinesSymbol;
		/** Multiline symbol */
		private Character multilineSymbol;
		/** Current default directory */
		private Path currentDirectory;
		/** Map that stores shared data */
		private Map<String, Stack<Path>> map;

		/**
		 * Constructor that initializes object.
		 * 
		 * @param commands Provided map that contains commands.
		 */
		public MyEnvironment(SortedMap<String, ShellCommand> commands) {
			this.commands = commands;
			promptSymbol = Character.valueOf('>');
			morelinesSymbol = Character.valueOf('\\');
			multilineSymbol = Character.valueOf('|');
			currentDirectory = Paths.get(".").toAbsolutePath().normalize();
			map = new HashMap<>();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeln(String text) throws ShellIOException {
			try {
				System.out.println(text);
			} catch (Exception e) {
				throw new ShellIOException("Error.");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(String text) throws ShellIOException {
			try {
				System.out.print(text);
			} catch (Exception e) {
				throw new ShellIOException("Error.");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String readLine() throws ShellIOException {
			try {
				return scanner.nextLine().trim();
			} catch (Exception e) {
				throw new ShellIOException("Error.");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Path getCurrentDirectory() {
			return currentDirectory;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setCurrentDirectory(Path path) {
			if (Files.isDirectory(path)) {
				currentDirectory = path.toAbsolutePath().normalize();	
			} else {
				throw new ShellIOException("Given path is not a directory.");
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getSharedData(String key) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
			
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setSharedData(String key, Object value) {
			if (!map.containsKey(key)) {
				map.put(key, new Stack<>());
			}
			
			map.get(key).push((Path) value);
		}

	}

}
