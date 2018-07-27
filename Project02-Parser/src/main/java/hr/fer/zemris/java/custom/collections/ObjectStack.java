package hr.fer.zemris.java.custom.collections;

/**
 * Class that simulates stack like collection.
 * It contains method like pop, push and peek that you 
 * would expect stack to have. This class is used as 
 * adapter of arrayIndexedCollection class.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ObjectStack {

	private ArrayIndexedCollection array;
	
	/**
	 * Default constructor that initializes stack.
	 */
	public ObjectStack() {
		array = new ArrayIndexedCollection();
	}
	
	/**
	 * Method that checks if stack is empty.
	 * 
	 * @return <code>true</code> if stack is empty, 
	 * else return <code>false</<code>.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Checks size of the stack.
	 * 
	 * @return current size of stack.
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * Method that clears the stack an sets it's size
	 * to 0.
	 */
	public void clear() {
		array.clear();
	}
	
	/**
	 * Method that puts one object on the top of the stack.
	 * 
	 * @param value Object that will be pushed on the stack.
	 * @throws NullPointerException if value is null.
	 */
	public void push(Object value) {
		if (value == null) {
			throw new NullPointerException("Value can not be null.");
		}
		
		array.add(value);
	}
	
	/**
	 * Method that removes one object from the stack.
	 * 
	 * @return Object on top of the stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public Object pop() {
		checkIfEmpty();
		
		Object object = array.get(array.size()-1);
		
		array.remove(array.size()-1);
		
		return object;
	}
	
	/**
	 * Method that checks what is on top of the stack.
	 * 
	 * @return Object on top of the stack.
	 * @throws EmptyStackException if stack is empty.
	 */
	public Object peek() {
		checkIfEmpty();
		
		return array.get(array.size()-1);
	}
	
	/**
	 * Method that checks if stack is empty and throws exceptiom
	 * if it is.
	 * 
	 * @throws EmptyStackException if stack is empty.
	 */
	private void checkIfEmpty() throws EmptyStackException{
		if (array.isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
	}
}
