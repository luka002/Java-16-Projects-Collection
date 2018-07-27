package hr.fer.zemris.java.custom.collections;

/**
 * Class that stores elements, size and capacity of collection
 * and includes various method for managing elements in it.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private int capacity;
	private Object[] elements;

	/**
	 * Default capacity that is used if one is not provided.
	 */
	private final static int DEFAULT_SIZE = 16;

	/**
	 * Default constructor that allocates array of capacity DEFAULT_SIZE.
	 */
	public ArrayIndexedCollection() {
		size = 0;
		capacity = DEFAULT_SIZE;
		elements = new Object[16];
	}

	/**
	 * Constructor that is given initial capacity of collection.
	 * 
	 * @param initialCapacity Initial capacity of collection.
	 * @throws IllegalArgumentException If capacity is set to less than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity can not be less than 1.");
		}

		size = 0;
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * Constructor that copies element from given collection into
	 * this collection.
	 * 
	 * @param collection Collection that will be copied into
	 * this one.
	 */
	public ArrayIndexedCollection(Collection collection) {
		this();
		checkSize(collection);
		addAll(collection);
	}

	/**
	 * Constructor that copies element from given collection into
	 * this collection and sets initial capacity.
	 * 
	 * @param collection Collection that will be copied into this one.
	 * @param initialCapacity Provided initial collection capacity.
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity);
		checkSize(collection);
		addAll(collection);
	}

	/**
	 * When using constructor that copies provided collection elements
	 * into this collection this method checks if the given collection is
	 * <code>null</code> or larger than capacity and throws exception if empty
	 * or doubles the capacity if current capacity is to small.
	 * 
	 * @param collection Collection to be checked for it's size.
	 * @throws NullPointerException If collection is empty. 
	 */
	private void checkSize(Collection collection) throws NullPointerException {
		if (collection.isEmpty()) {
			throw new NullPointerException("Added collection can not be null.");
		}

		if (collection.size() < this.capacity) {
			this.elements = new Object[collection.size()];
		}
	}
	
	/**
	 * Method that finds the object that is stored in backing array
	 * at position index. Valid indexes are 0 to size-1.
	 * 
	 * @param index Index where the object is stored.
	 * @return Object at index <code>index</code>.
	 * @throws IndexOutOfBoundsException if (index < 0) or (index >= size).
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException("Index has to be greater than -1 and smaller than size");
		}
		
		return elements[index];
	}
	
	/**
	 * Method that searches the collection for the first occurrence of the given value.
	 *  
	 * @param value Value whose index is being searched for. it can be null
	 * @return index of the first occurrence of the given value
	 * or -1 if the value is not found.
	 */
	public int indexOf(Object value) {
		if (value != null) {
			for (int i = 0; i < this.size; i++) {
				if (this.elements[i].equals(value)) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position 
	 * in array. Elements at greater positions must be shifted one place toward 
	 * the end, so that an empty place is created at position at which the 
	 * value will be added. 
	 * The legal positions are 0 to size (both are included).
	 * 
	 * @param value Object to be inserted.
	 * @param position Position at which object will be inserted.
	 * @throws IndexOutOfBoundsException if position<0 or position>size.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index has to be greater than -1 and smaller than size+1");
		}
		
		if (this.size == 0) {
			add(value);
			return;
		}
		
		if (this.size == this.capacity) {
			doubleCapacity();
		}
		
		for (int i = this.size-1; i >= position; i--) {
			this.elements[i+1] = this.elements[i];
		}
		
		this.elements[position] = value;
		size++;
	}

	/**
	 * Doubles capacity of the collection.
	 */
	private void doubleCapacity() {
		Object[] newArray = new Object[this.capacity*2];

		for (int i = 0; i < this.capacity; i++) {
			newArray[i] = this.elements[i];
		}

		this.capacity *= 2;
		this.elements = newArray;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("New value can not be null.");
		}
		
		if (this.size == this.capacity) {
			doubleCapacity();
		} 
		
		this.elements[this.size] = value;
		this.size++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		
		int index = indexOf(value);
		
		if (index == -1) {
			return false;
		}
		
		remove(index);
		return true;
	}

	/**
	 * Method that removes object from collection at specified index 
	 * and shifts later elements to the beginning.
	 * 
	 * @param index Remove object from if.
	 * @throws IndexIOutOfBoundsException if index <0 or index > size-1.
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException("Index has to be greater than -1 and smaller than size");
		}
		
		for (int i = index; i < this.size-1; i++) {
			this.elements[i] = this.elements[i+1];
		}
		
		this.elements[size-1] = null;
		this.size--;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] arrayOfObjects = new Object[this.size];
		
		if (size == 0) {
			throw new UnsupportedOperationException("Array is empty");
		}
		
		for (int i = 0; i < size; i++) {
			arrayOfObjects[i] = this.elements[i];
		}
		
		return arrayOfObjects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
	}

}
