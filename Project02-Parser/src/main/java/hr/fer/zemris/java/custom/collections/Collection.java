package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents some general collection of objects.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Collection {

	/**
	 * Constructor for class Çollection.
	 */
	protected Collection() {
	}

	/**
	 * Method that checks if collection is empty.
	 * 
	 * @return <code>true</code> if collection is empty.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Method that checks number of currently stored
	 * objects in this collections.
	 * 
	 * @return Number of currently stored objects in this collections.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method that adds the given object into this collection.
	 * If collection is full capacity will be doubled.
	 * 
	 * @param value Object to be added in this collection.
	 * @throws NullPointerException if value is null.
	 */
	public void add(Object value) {
	}

	/**
	 * Method that checks if collection contains given object.
	 * 
	 * @param value Object that is checked if it's in this collection.
	 * <code>null</code> is also valid.
	 * @return <code>true</code> if collection contains given object,
	 * else returns <code>false</code>.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Method that removes given object from collection if that object
	 * is in the collection.
	 * 
	 * @param value Object to be removed from the collection.
	 * @return <code>true</code> if object is removed, else
	 * return <code>false</code>.
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method that allocates new array with size equals to the size
	 * of this collections and fills it with collection content and
	 * returns the array.
	 * 
	 * @return array of collection objects.
	 * @throws UnsupportedOperationException is array is empty.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method that processes each element of this collection.
	 * 
	 * @param processor Every object is processed by it.
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Method that adds into the current collection all 
	 * elements from the given collection.
	 * 
	 * @param other Collection which elements will be added into 
	 * this collection.
	 */
	public void addAll(Collection other) {
		
		/**
		 * Class for adding element in this collection.
		 * 
		 * @author Luka Grgić
		 */
		class Processor extends hr.fer.zemris.java.custom.collections.Processor {
			
			/**
			 * Method that adds element into this collection.
			 * 
			 * @param value Element to be added into this collection.
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
			
		}
		
		Processor processor = new Processor();
		
		other.forEach(processor);
	}

	/**
	 * Method that removes all objects from the collection.
	 */
	public void clear() {
	}

}
