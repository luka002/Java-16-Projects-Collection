package hr.fer.zemris.java.hw04.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that represents a map that contains key-value pair stored
 * in hash table.
 * 
 * @author Luka Grgić
 * @version 1.0
 *
 * @param <K> Key of the map that can not be null.
 * @param <V> Value of the map.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	/**
	 * Class that represents one key-value pair.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 * @param <K> Key of the map that can not be null.
	 * @param <V> Value of the map.
	 */
	public static class TableEntry<K, V> {
		/** Key of the map */
		private K key;
		/** Value of the map */
		private V value;
		/** Next table entry */
		private TableEntry<K, V> next;

		/**
		 * Constructor for initializing one entry.
		 * 
		 * @param key Key of the map.
		 * @param value Value of the map.
		 * @param next Next table entry.
		 * @throws NullPointerException if key is null.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new NullPointerException("Key can't be null.");
			}

			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * @return Returns key.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * @return Returns value.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @param value Sets value.
		 */
		public void setValue(V value) {
			this.value = value;
		}

	}

	/** Array that holds hash table entries */
	private TableEntry<K, V>[] table;
	/** Size of hash table */
	private int size;
	/** Number of times hash table has been modified */
	private int modificationCount;
	/** Default number of hash table slots */
	private static final int DEFAULT_SIZE = 16;

	/**
	 * Default constructor that sets table to 16 slots.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Constructor that accepts initial capacity.
	 * 
	 * @param capacity Number of table slots.
	 * @throws IllegalArgumentException if size is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Size can't be less than 1.");
		}

		this.size = 0;
		this.modificationCount = 0;
		int realCapacity;

		for (realCapacity = 1; realCapacity < capacity; realCapacity *= 2) {
		}

		table = (TableEntry<K, V>[]) new TableEntry[realCapacity];
	}

	/**
	 * Method that adds one entry into the table.
	 * 
	 * @param key Key of the entry.
	 * @param value Value of entry.
	 */
	public void put(K key, V value) {
		int hashSlot = calculateSlot(key, table.length) ;
		
		put(key, value, hashSlot, table);
	}
	
	/**
	 * Method that adds one entry to provided table
	 * in provided slot.
	 * 
	 * @param key Key of the entry.
	 * @param value Value of the entry.
	 * @param hashSlot Slot in which to put entry.
	 * @param table Table in which to put entry.
	 */
	private void put(K key, V value, int hashSlot, TableEntry<K, V>[] table) {
		if (size + 1 > table.length*0.75) {
			this.table = table = changeCapacity(table);
			hashSlot = calculateSlot(key, table.length);
		}

		if (table[hashSlot] == null) {
			table[hashSlot] = new TableEntry<>(key, value, null);
			size++;
			modificationCount++;
			return;
		}

		TableEntry<K, V> entry = table[hashSlot];

		while (true) {
			if (entry.key.equals(key)) {
				entry.setValue(value);
				modificationCount++;
				return;
			} else if (entry.next == null) {
				entry.next = new TableEntry<K, V>(key, value, null);
				size++;
				modificationCount++;
				return;
			}

			entry = entry.next;
		}
	}

	/**
	 * Method that doubles table capacity.
	 * 
	 * @param table Table that capacity will be doubled.
	 * @return table
	 */
	@SuppressWarnings("unchecked")
	private TableEntry<K, V>[] changeCapacity(TableEntry<K, V>[] table) {
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length*2];
		TableEntry<K, V> entry;
		size = 0;
		
		for (int i = 0; i < table.length; i++) {
			entry = table[i];

			if (entry == null)
				continue;

			while (true) {
				int hashSlot = calculateSlot(entry.key, newTable.length) ;
				
				put(entry.key, entry.value, hashSlot, newTable);
				
				if (entry.next == null) {
					break;
				}

				entry = entry.next;
			}
		}
		
		return newTable;
	}
	
	/**
	 * Method that clears hash table.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = (TableEntry<K, V>[]) new TableEntry[table.length];
		size = 0;
		modificationCount++;
	}

	/**
	 * Method that returns value that is pair of provided key.
	 * 
	 * @param key_ Key of value that will be returned.
	 * @return Value of provided key.
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key_) {
		if (key_ == null)
			return null;

		K key = null;

		try {
			key = (K) key_;
		} catch (ClassCastException ex) {
			return null;
		}

		int hashSlot = calculateSlot(key, table.length);

		if (table[hashSlot] == null) {
			return null;
		}

		TableEntry<K, V> entry = table[hashSlot];

		while (true) {
			if (entry.key.equals(key)) {
				return entry.value;
			} else if (entry.next == null) {
				break;
			}

			entry = entry.next;
		}

		return null;
	}

	/**
	 * @return Size of hash table.
	 */
	public int size() {
		return size;
	}

	/**
	 * Method that checks if table contains provided key.
	 * 
	 * @param key_ Key that is checked if it is in the table.
	 * @return true if table contains key, else false.
	 */
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key_) {
		if (key_ == null)
			return false;

		K key = null;

		try {
			key = (K) key_;
		} catch (ClassCastException ex) {
			return false;
		}

		int hashSlot = calculateSlot(key, table.length);

		if (table[hashSlot] == null) {
			return false;
		}

		TableEntry<K, V> entry = table[hashSlot];

		while (true) {
			if (entry.key.equals(key)) {
				return true;
			} else if (entry.next == null) {
				break;
			}

			entry = entry.next;
		}

		return false;
	}

	/**
	 * Method that checks if table contains provided value.
	 * 
	 * @param value Value that is checked if it is in the table.
	 * @return true if value is in the table, else false.
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> entry;

		for (int i = 0; i < table.length; i++) {
			entry = table[i];

			if (entry == null)
				continue;

			while (true) {
				if (entry.value == null) {
					if (entry.value == value) return true; 
				} else if (entry.value.equals(value)) {
						return true;
				} 
				
				if (entry.next == null) {
						break;
				}	

				entry = entry.next;
			}

		}

		return false;
	}

	/**
	 * Method that removes entry if provided key
	 * is in the table.
	 * 
	 * @param key_ Key that is checked if it is in the table.
	 */
	@SuppressWarnings("unchecked")
	public void remove(Object key_) {
		if (key_ == null) return;

		K key = null;

		try {
			key = (K) key_;
		} catch (ClassCastException ex) {
			return;
		}

		int hashSlot = calculateSlot(key, table.length);

		if (table[hashSlot] == null)
			return;

		TableEntry<K, V> entry = table[hashSlot];

		if (entry.key.equals(key)) {
			table[hashSlot] = entry.next;
			entry.next = null;
			size--;
			modificationCount++;
			return;
		}

		while (entry.next != null) {
			if (entry.next.key.equals(key)) {
				TableEntry<K, V> tmp = entry.next;

				entry.next = entry.next.next;
				tmp.next = null;
				size--;
				modificationCount++;
				return;
			}

			entry = entry.next;
		}

	}

	/**
	 * @return true if table is empty, else false.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * @return All entries in the table in string.
	 */
	public String toString() {
		TableEntry<K, V> entry;
		StringBuilder string = new StringBuilder("[");

		for (int i = 0; i < table.length; i++) {
			entry = table[i];

			if (entry == null)
				continue;

			while (entry != null) {
				string.append(entry.key + "=" + entry.value + ", ");

				entry = entry.next;
			}

		}

		if (string.length() == 1) return "";
		
		string.replace(string.length() - 2, string.length() - 1, "]");
		return string.toString();
	}

	/**
	 * Calculates hash slot from provided key.
	 * @param key
	 * @param slots
	 * @return
	 */
	private int calculateSlot(K key, int slots) {
		int slotValue = key.hashCode() % slots;

		return slotValue >= 0 ? slotValue : -slotValue;
	}
	
	/**
	 * @return Iterator that goes trough table elements.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Iterator of SimpleHashtable.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		/** Copy of modificationCount */
		private int modificationCountCopy;
		/** Current slot of table */
		private int currentSlot;
		/** Table elements already iterated trough */
		private int elementsProcessed;
		/** Entry */
		private TableEntry<K, V> entry;
		/** Next entry */
		private TableEntry<K, V> nextEntry;
		/** Is remove enabled flag */
		private boolean removeEnabled;
		/** Is first next() called flag*/
		private boolean firstNextCalled;
		
		/**
		 * Constructor.
		 */
		public IteratorImpl() {
			this.modificationCountCopy = modificationCount;
			this.currentSlot = 0;
			this.elementsProcessed = 0;
			this.entry = null;
			this.nextEntry = table[this.currentSlot];
			this.removeEnabled = true;
			this.firstNextCalled = false;
		}

		/**
		 * Method that checks if there are more entries
		 * to iterate trough.
		 * 
		 * @return true if not last entry in table, else false.
		 */
		public boolean hasNext() {
			checkIfModified();
			
			if (elementsProcessed < size) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Method that gets next entry in table.
		 * 
		 * @return next entry.
		 * @throws NoSuchElementException if it has iterated
		 * trough all elements.
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			checkIfModified();
			
			while (currentSlot < table.length && !isEmpty()) {

				if (nextEntry != null) {
					entry = nextEntry;
					nextEntry = nextEntry.next;
											
					elementsProcessed++;
					removeEnabled = true;
					firstNextCalled = true;
					return entry;
				}
				
				currentSlot++;
				nextEntry = table[currentSlot];
			}
			
			throw new NoSuchElementException("Already went trough all elements.");
		}
		
		/**
		 * Removes last element that was returned with next() method.
		 */
		public void remove() {
			checkIfModified();
			
			if (!removeEnabled || !firstNextCalled) {
				throw new IllegalStateException("Can not call remove two times in a row.");
			}
			
			SimpleHashtable.this.remove(entry.key);
			
			modificationCountCopy++;
			elementsProcessed--;
			
			removeEnabled = false;
		}
		
		/**
		 * Checks if table has been modified after iterator had been created.
		 */
		private void checkIfModified() {
			if (modificationCount != modificationCountCopy) {
				throw new ConcurrentModificationException("Can not modify while iteraring.");
			}
		}
		
	}
	
}
