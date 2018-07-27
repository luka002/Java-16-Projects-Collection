package hr.fer.zemris.java.hw08.shell.name;

/**
 * Interface that generates parts of file name
 * and writes it in the NameBuilderInfo.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface NameBuilder {

	/**
	 * Generates parts of file name
	 * and writes it in the NameBuilderInfo.
	 * 
	 * @param info NameBuilderInfo
	 */
	void execute(NameBuilderInfo info);
	
}
