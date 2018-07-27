package hr.fer.zemris.java.hw04.db;

/**
 * Interface for filtering data.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface IFilter {
	
	/**
	 * Method that accepts record and filters it.
	 * 
	 * @param record Provided StudentRecord.
	 * @return true if condition id met, else false.
	 */
	public boolean accepts(StudentRecord record);
	
}