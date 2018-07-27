package hr.fer.zemris.java.hw06.observer1;

/**
 * Concrete class that implements observer. It calculates
 * square value from value that has been changed.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Method that prints out square value from value that has been changed.
	 * 
	 * @param istorage object trough which value can be accessed.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		
		System.out.println("Provided new value: " + value + ", square is " + value*value);
	}

}
