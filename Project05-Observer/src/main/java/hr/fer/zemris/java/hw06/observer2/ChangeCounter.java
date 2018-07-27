package hr.fer.zemris.java.hw06.observer2;

/**
 * Concrete class that implements observer. It prints out 
 * how many times value has been changes after registration.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** Change counter */
	private int changeCounter;	
	
	/**
	 * Constructor that initializes object.
	 */
	public ChangeCounter() {
		this.changeCounter = 0;
	}

	/**
	 * Method that prints out how many times value has been changes
	 * after registration.
	 * 
	 * @param istorage object trough which value can be accessed.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		
		System.out.println("Number of value changes since tracking: " + ++changeCounter);
	
	}

}
