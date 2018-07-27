package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents special kind of Map that allows user to store
 * multiple values for the same key. Keys are instances of class String.
 * Values associated with those keys are instances of class ValueWrapper.
 * Value associated with string is made like stack. For value added for given 
 * key, value is added to the top of the stack. Class MultistackEntry represents
 * one node.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ObjectMultistack {
	
	/** Map that stores key-value pair */
	private Map<String, MultistackEntry> map;
	
	/**
	 * Constructor that initializes object.
	 */
	public ObjectMultistack() {
		this.map = new HashMap<>();
	}

	/**
	 * Method that for given key puts value on top of the stack.
	 * 
	 * @param name Key.
	 * @param valueWrapper Value.
	 * @throws NullPointerException if provided value is null.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (valueWrapper == null) throw new NullPointerException("Object can not be null.");
		
		if (isEmpty(name)) {
			map.put(name, new MultistackEntry(valueWrapper, null));
		} else {
			MultistackEntry oldEntry = map.get(name);
			MultistackEntry newEntry = new MultistackEntry(valueWrapper, oldEntry);
			
			map.put(name, newEntry);
		}
	}

	/**
	 * Method that removes value from top of the stack
	 * for given key.
	 * 
	 * @param name Key.
	 * @return Value from top of the stack of given key.
	 * @throws EmptyStackException if stack is empty.
	 */
	public ValueWrapper pop(String name) {
		if (isEmpty(name)) throw new EmptyStackException("Given key does not exist.");
		
		MultistackEntry entry = map.get(name);
		
		if (entry.next == null) {
			map.remove(name);
			return entry.value;
		} else {
			map.put(name, entry.next);
			return entry.value;
		}
	}

	/**
	 * Method that checks value from top of the stack
	 * for given key.
	 * 
	 * @param name Key.
	 * @return Value from top of the stack of given key.
	 * @throws EmptyStackException if stack is empty.
	 */
	public ValueWrapper peek(String name) {
		if (isEmpty(name)) throw new EmptyStackException("Given key does not exist.");
		
		return map.get(name).value;
	}

	/**
	 * Checks if given key exists in a map.
	 * 
	 * @param name KEy.
	 * @return true if key exists, else false.
	 */
	public boolean isEmpty(String name) {
		return !map.containsKey(name);
	}

	/**
	 * Class that represents one node on the stack.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class MultistackEntry {
	
		/** Value */
		private ValueWrapper value;
		/** Next node */
		private MultistackEntry next;
		
		/**
		 * Constructor that initializes object.
		 * 
		 * @param value Value.
		 * @param next Next node.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
		
	}
	
}