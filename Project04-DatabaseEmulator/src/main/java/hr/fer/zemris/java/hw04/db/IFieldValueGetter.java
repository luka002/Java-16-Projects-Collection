package hr.fer.zemris.java.hw04.db;

/**
 * Strategy that is responsible for obtaining a requested field
 * value from given StudentRecord.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface IFieldValueGetter {

	/**
	 * @param record StudentRecord.
	 * @return String.
	 */
	public String get(StudentRecord record);
	
}
