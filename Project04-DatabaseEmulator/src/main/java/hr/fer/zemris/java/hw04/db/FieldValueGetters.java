package hr.fer.zemris.java.hw04.db;

/**
 * Class that returns IFieldValueGetter strategy.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class FieldValueGetters {

	/**
	 * Strategy that returns first name from provided StudentRecord.
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> {
		return record.getFirstName();
	};

	/**
	 * Strategy that returns last name from provided StudentRecord.
	 */
	public static final IFieldValueGetter LAST_NAME = record -> {
		return record.getLastName();
	};

	/**
	 * Strategy that returns jmbag from provided StudentRecord.
	 */
	public static final IFieldValueGetter JMBAG = record -> {
		return record.getJmbag();
	};

}
