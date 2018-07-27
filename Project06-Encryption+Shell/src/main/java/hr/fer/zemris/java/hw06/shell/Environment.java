 package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface that defines methods for easier interaction with shell.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface Environment {

	/**
	 * Reads user input.
	 * 
	 * @return User input.
	 * @throws ShellIOException if there was problem with input.
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes provided text on console.
	 * 
	 * @param text Provided text.
	 * @throws ShellIOException if there was problem with output.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes provided text on console and treminates the line.
	 * 
	 * @param text Provided text.
	 * @throws ShellIOException if there was problem with output.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Return sorted map that contains shell commands.
	 * 
	 * @return Sorted map that contains shell commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns multiline symbol.
	 * 
	 * @return Multiline symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol.
	 * 
	 * @param symbol Multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol.
	 * 
	 * @return Prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol.
	 * 
	 * @param symbol Prompt symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns more symbol.
	 * 
	 * @return More symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets moreline symbol.
	 * 
	 * @param symbol Moreline symbol.
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Returns current absolute normalized path.
	 * 
	 * @return current absolute normalized path.
	 * @throws ShellIOException if path is not a directory.
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets current absolute normalized path as default shell
	 * directory.
	 * 
	 * @param path Path to be set.
	 * @throws ShellIOException if path is not a directory.
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Returns shared data from the map based on
	 * the key.
	 * 
	 * @param key Key
	 * @return shared data.
	 */
	Object getSharedData(String key);
	
	/**
	 * Sets shared data in a map.
	 * 
	 * @param key Key.
	 * @param value Value.
	 */
	void setSharedData(String key, Object value);

}
