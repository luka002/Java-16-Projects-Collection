package hr.fer.zemris.java.hw06.observer2;

/**
 * Class that stores state of the subject.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class IntegerStorageChange {
	/** The subject */
	private IntegerStorage storage;
	/** Value before change */
	private int valueBeforeChange;
	/** New value */
	private int newValue;

	/**
	 * Constructor that initializes object.
	 * 
	 * @param storage The subject.
	 * @param valueBeforeChange Value before change.
	 * @param newValue New value.
	 */
	public IntegerStorageChange(IntegerStorage storage, int valueBeforeChange, int newValue) {
		this.storage = storage;
		this.valueBeforeChange = valueBeforeChange;
		this.newValue = newValue;
	}

	/**
	 * @return The subject.
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * @return Value before change.
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}
	
	/**
	 * @return Returns new value. 
	 */
	public int getNewValue() {
		return newValue;
	}

}
