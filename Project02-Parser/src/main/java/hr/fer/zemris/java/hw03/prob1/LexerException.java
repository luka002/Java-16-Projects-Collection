package hr.fer.zemris.java.hw03.prob1;

 /**
  * Thrown to indicate given data can't be tokenized.
  * 
  * @author Luka Grgić
  * @version 1.0
  */
public class LexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
     * Constructs an LexerException with no
     * detail message.
     */
	public LexerException() {
		super();
	}

	/**
     * Constructs an LexerException with the
     * specified detail message.
     *
     * @param arg0 The detail message.
     */
	public LexerException(String arg0) {
		super(arg0);
	}
	
}