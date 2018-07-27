package hr.fer.zemris.java.custom.collections;

public class EmptyStackException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EmptyStackException() {
		super();
	}

	public EmptyStackException(String arg0) {
		super(arg0);
	}
	
}
