package hr.fer.zemris.java.hw08.shell.name;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores NameBuilder list.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class MainNameBuilder implements NameBuilder {
	/** NameBuilder list */
	private List<NameBuilder> list;
	/** Holds renamed file name */
	private StringBuilder builder;
	
	/**
	 * Constructor.
	 */
	public MainNameBuilder() {
		this.list = new ArrayList<>();
	}

	/**
	 * Executes execute method from all the NameBuilders
	 * in the list.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).execute(info);
		}
		
		builder = info.getStringBuilder();
	}
	
	/**
	 * Adds NameBuilder to the list.
	 * 
	 * @param builder NameBuilder.
	 */
	public void addNameBuilder(NameBuilder builder) {
		list.add(builder);
	}

	/**
	 * @return Returns builder.
	 */
	public StringBuilder getStringBuilder() {
		return builder;
	}
	
}
