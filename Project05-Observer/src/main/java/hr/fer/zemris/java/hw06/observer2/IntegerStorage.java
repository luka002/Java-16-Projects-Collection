package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents "The Subject" in "Observer pattern" design pattern.
 * This class stores list of IntegerStorageObservers and value.
 * When value changes all observers in the list are signaled and
 * valueChanged() method is called upon them. It also stores 
 * IntegerStorageChange which stores current and previous state.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class IntegerStorage {
	/** Observed value */
	private int value;
	/** List of observers */
	private List<IntegerStorageObserver> observers;
	/** State storage */
	private IntegerStorageChange storage;
	
	/**
	 * Constructor that initializes object.
	 * 
	 * @param initialValue Initial value.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}

	/**
	 * Method that adds observer to the list.
	 * 
	 * @param observer Observer to be added.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Method that removes observer from the list.
	 * 
	 * @param observer Observer to be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Method that removes all observers from the list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * @return Returns value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Method that sets value and signals all observers that
	 * value has changed.
	 * 
	 * @param value New value.
	 */
	public void setValue(int value) {
		if (this.value != value) {
			storage = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			
			if (observers != null) {
				List<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
				
				for (IntegerStorageObserver observer : observersCopy) {
					observer.valueChanged(storage);
				}
			}
			
		}
	}
	
}