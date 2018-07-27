package hr.fer.zemris.java.hw06.observer1;

/**
 * Concrete class that implements observer. It prints out 
 * value from subject that has been changed multiplied by 2.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	/** Allowed changes */
	private int changesAllowed;
	/** Changes made */
	private int changesMade;
	
	/**
	 * Constructor that initializes object.
	 * 
	 * @param changesAllowed Allowed changes
	 */
	public DoubleValue(int changesAllowed) {
		this.changesAllowed = changesAllowed;
		this.changesMade = 0;
	}

	/**
	 * Method that prints out value from subject that has been
	 * changed multiplied by 2.
	 * 
	 * @param istorage object trough which value can be accessed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + 2*istorage.getValue());
	
		if (++changesMade >= changesAllowed) {
			istorage.removeObserver(this);
		}
	}

}
