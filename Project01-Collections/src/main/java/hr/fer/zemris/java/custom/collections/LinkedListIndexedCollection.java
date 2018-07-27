package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents implementation of linked list.
 * It contains size of the list, first node and last node 
 * of the list.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Default constructor that sets everything to 0 or null.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructor that receives another collection which elements are 
	 * added to this list.
	 * 
	 * @param collection collection which elements will be added
	 * in this list.
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this();

		Object[] objects = collection.toArray();

		for (Object object : objects) {
			add(object);
		}
	}
	
	/**
	 * Method that searches the collection for the first occurrence of the given value.
	 *  
	 * @param value Value whose index is being searched for. It can be null.
	 * @return index of the first occurrence of the given value
	 * or -1 if the value is not found.
	 */
	public int indexOf(Object value) {
		ListNode node = first;
		
		for (int i = 0; i < size; i++) {
			if (node.getValue().equals(value)) {
				return i;
			}
			
			node = node.getNext();
		}
		
		return -1;
	}

	/**
	 * Method that finds the object that is stored in list
	 * at position index. Valid indexes are 0 to size-1.
	 * 
	 * @param index Index where the object is stored.
	 * @return Object at index <code>index</code>.
	 * @throws IndexOutOfBoundsException if (index < 0) or (index >= size).
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index has to be greater than -1 and smaller than size");
		}

		return searchForNode(index).getValue();
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position 
	 * in list. Elements at greater positions must be shifted one place toward 
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
		
		ListNode node = new ListNode(value);
		
		if (position == 0) {
			node.setNext(first);
			first.setPrevious(node);
			first = node;
		} else if (position == size) {
			node.setPrevious(last);
			last.setNext(node);
			last = node;
		} else {
			ListNode nodeToReplace = searchForNode(position);
			
			nodeToReplace.getPrevious().setNext(node);
			node.setPrevious(nodeToReplace.getPrevious());
			node.setNext(nodeToReplace);
			nodeToReplace.setPrevious(node);
		}
		
		size++;
	}

	/**
	 * Search for node at given index.
	 * 
	 * @param index Search for node at this index.
	 * @return Node at specified index
	 */
	private ListNode searchForNode(int index) {
		ListNode node;

		if (index < size / 2) {
			node = first;

			for (int i = 0; i < index; i++) {
				node = node.getNext();
			}
		} else {
			node = last;

			for (int i = size - 1; i > index; i--) {
				node = node.getPrevious();
			}
		}

		return node;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Value can not be null.");
		}

		ListNode node = new ListNode(value);

		if (size == 0) {
			first = last = node;
		} else {
			last.setNext(node);
			node.setPrevious(last);
			last = node;
		}

		size++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(Object value) {
		ListNode node = first;
		
		for (int i = 0; i < size; i++) {
			if (node.value.equals(value)) {
				return true;
			}
			
			node = node.getNext();
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
	 * Method that removes object from list at specified index 
	 * and shifts later elements to the beginning.
	 * 
	 * @param index Remove object from if.
	 * @throws IndexIOutOfBoundsException if index <0 or index > size-1.
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException("Index has to be greater than -1 and smaller than size");
		}
		
		if (index == 0 && size() == 1) {
			first = last = null;
		} else if (index == 0) {
			first.getNext().setPrevious(null);
			first = first.getNext();
		} else if (index == size-1) {
			last.getPrevious().setNext(null);
			last = last.getPrevious();
		} else {
			ListNode node = searchForNode(index);
			
			node.getPrevious().setNext(node.getNext());
			node.getNext().setPrevious(node.getPrevious());
			node = null;
		}
		
		size--;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		Object[] arrayOfObjects = new Object[size];
		
		if (first == null) {
			throw new UnsupportedOperationException("Array is empty");
		}
		
		ListNode node = first;
		
		for (int i = 0; i < size; i++) {
			arrayOfObjects[i] = node.getValue();
			node = node.getNext();
		}
		
		return arrayOfObjects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		
		for (int i = 0; i < this.size; i++) {
			processor.process(node.getValue());
			node = node.getNext();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Class that represents a node in a list.
	 * It has previous node, next node and object value
	 * stored in it.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;

		/**
		 * Constructor for one node.
		 * 
		 * @param value Value of this node.
		 */
		public ListNode(Object value) {
			this.value = value;
			previous = next = null;
		}

		/**
		 * @return current previous node.
		 */
		public ListNode getPrevious() {
			return previous;
		}

		/**
		 * @param previous Previous node to set.
		 */
		public void setPrevious(ListNode previous) {
			this.previous = previous;
		}

		/**
		 * @return current next node.
		 */
		public ListNode getNext() {
			return next;
		}

		/**
		 * @param next Next node to set.
		 */
		public void setNext(ListNode next) {
			this.next = next;
		}

		/**
		 * @return current node value.
		 */
		public Object getValue() {
			return value;
		}

	}

}
