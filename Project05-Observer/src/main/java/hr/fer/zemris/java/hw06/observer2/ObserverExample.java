package hr.fer.zemris.java.hw06.observer2;

/**
 * Program that creates IntegerStorage class that represents
 * "The Subject" and classes that implement IntegerStorageObserver 
 * which represent "Observer" and registers those observers to the
 * subject.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ObserverExample {
	
	/**
	 * Method that first executes when program is started.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
	
		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
	}
}
