package hr.fer.zemris.java.hw08.shell.name;

/**
 * Stores string part of the file name.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class LiteralNameBuilder implements NameBuilder {
	/** String literal */
	private String literal;
	
	/**
	 * Constructor.
	 * 
	 * @param literal Given string.
	 */
	public LiteralNameBuilder(String literal) {
		this.literal = literal;
	}

	/**
	 * Appends literal into the info string builder.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(literal);
	}

}
