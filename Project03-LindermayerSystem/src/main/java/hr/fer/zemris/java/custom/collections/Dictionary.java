package hr.fer.zemris.java.custom.collections;

/**
 * Class for storing objects that have unique key
 * and value tied with that key.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Dictionary {

	/**
	 * Private inner class designed for storing keys and values.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class DictionaryValue {
		
		/** Provided key */
		Object key;
		/** Provided value */
		Object value;
		
		/**
		 * Constructor.
		 * 
		 * @param key Provided key.
		 * @param value Provided value.
		 */
		public DictionaryValue(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
	/** Collection for storing DictionaryValue objects */
	private ArrayIndexedCollection collection;
	
	/** 
	 * Constructor for initializing collection.
	 */
	public Dictionary() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if dictionary is empty.
	 * 
	 * @return True if dictionary is empty. Otherwise
	 * returns false.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Checks how many values does dictionary has.
	 * 
	 * @return Number of values stored in dictionary.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Removes all values from dictionary.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Puts one value with corresponding key in the dictionary.
	 * 
	 * @param key Key of dictionary object. Can't be null.
	 * @param value Value of dictionary object.
	 * @throws NullPointerException if key is null.
	 */
	public void put(Object key, Object value) {
		if (key == null) {
			throw new NullPointerException("Key can not be null.");
		}
		
		for (int i = 0, size = size(); i < size; i++) {
			DictionaryValue myValue = (DictionaryValue)collection.get(i);
			
			if (myValue.key.equals(key)) {
				myValue.value = value;
				return;
			}
		}
		
		collection.add(new DictionaryValue(key, value));
	}
	
	/**
	 * Returns value from provided key. If key does not exist returns null.
	 * 
	 * @param key Key that is searched for.
	 * @return Value of corresponding key if key is found, else null.
	 */
	public Object get(Object key) {
		for (int i = 0, size = size(); i < size; i++) {
			DictionaryValue myValue = (DictionaryValue)collection.get(i);
			
			if (myValue.key.equals(key)) {
				return myValue.value;
			}
		}
		
		return null;
	}
	
}
