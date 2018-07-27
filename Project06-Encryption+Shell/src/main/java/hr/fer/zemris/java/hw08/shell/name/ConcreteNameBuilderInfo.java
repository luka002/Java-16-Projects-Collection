package hr.fer.zemris.java.hw08.shell.name;

import java.util.regex.Matcher;

/**
 * Holds information of matcher and renamed file in builder.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class ConcreteNameBuilderInfo implements NameBuilderInfo {
	/** Matcher */
	private Matcher matcher;
	/** Renamed file */
	private StringBuilder builder;
	
	/**
	 * Contructor.
	 * 
	 * @param matcher matcher.
	 */
	public ConcreteNameBuilderInfo(Matcher matcher) {
		this.matcher = matcher;
		this.builder = new StringBuilder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StringBuilder getStringBuilder() {
		return builder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getGroup(int index) {
		return matcher.group(index);
	}

}
