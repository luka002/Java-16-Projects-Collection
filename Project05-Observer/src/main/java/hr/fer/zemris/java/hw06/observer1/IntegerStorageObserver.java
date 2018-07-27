package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer interface that provides method that should be called
 * when value has changed.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public interface IntegerStorageObserver {

	/**
	 * Method called when value is changed.
	 * 
	 * @param istorage object trough which state can be accessed.
	 */
	public void valueChanged(IntegerStorage istorage);

}