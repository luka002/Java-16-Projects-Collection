package hr.fer.zemris.java.hw08.shell.name;

/**
 * Interface that has two methods.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface NameBuilderInfo {

	/**
	 * Returns name of the renamed file.
	 * 
	 * @return name of the renamed file.
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * Gets group of the matcher.
	 * 
	 * @param index index of group.
	 * @return group of the matcher.
	 */
	String getGroup(int index);
	
}
