package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
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
 * Class that represent hexdump command.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Expects a single argument: file name, and produces hex-output.
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
		
		try (InputStream input = Files.newInputStream(path, StandardOpenOption.READ)) {
			byte[] buff = new byte[1024];
			int row = 0, rowCount = 0;
			
			while (true) {
				int r = input.read(buff);
				if (r < 1) break;
				
				StringBuilder hex = null, characters = null;
				boolean firstPassAfterRead = true, isWritten = false;
				
				for (int i = 0; i < r; i++) {
					isWritten = false;
					
					if (i%16 == 0) {
						if (!firstPassAfterRead) {
							env.writeln(hex.toString() + characters.toString());
							isWritten = true;
						}
						firstPassAfterRead = false;
						
						if (i+1 < r) {
							env.write(String.format("%08X: ", (i/16)*16+row));
						}
						rowCount++;
						hex = new StringBuilder("                       |                        | ");
						characters = new StringBuilder("                ");
					}
					
					int pos = (i%16)*3;
					hex.replace(pos, pos+2, String.format("%02X", buff[i]));
					
					char c = (buff[i] < 32 || buff[i] > 127) ? '.' : (char)buff[i];
					pos = i%16;
					characters.replace(pos, pos+2, Character.toString(c));
				}
				
				if (!isWritten) {
					env.writeln(hex.toString() + characters.toString());
				}
				
				row += rowCount;
			}
		} catch (IOException ex) {
			env.writeln("Error.");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();

		description.add("     Expects a single argument: file name, and produces hex-output.");

		return Collections.unmodifiableList(description);
	}

}
