package hr.fer.zemris.java.hw08.shell.name;

/**
 * Writes given group with min width in provided.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class SpecialNameBuilder implements NameBuilder {
	/** Group number */
	private int groupNumber;
	/** Min width*/
	private String minWidth;
	
	/**
	 * Constructor.
	 * 
	 * @param groupNumber group number.
	 * @param minWidth min width.
	 */
	public SpecialNameBuilder(int groupNumber, String minWidth) {
		this.groupNumber = groupNumber;
		this.minWidth = minWidth;
	}

	/**
	 * Appends group onto the info string builder.
	 */
	@Override
	public void execute(NameBuilderInfo info) {
		String string = info.getGroup(groupNumber);
		
		if (minWidth != null) {
			string = String.format("%" + Integer.parseInt(minWidth) + "s", string);
			
			if (minWidth.charAt(0) == '0') {
				string = string.replace(' ', '0');
			}
		}
		
		info.getStringBuilder().append(string.toString());
	}

}
