package hr.fer.zemris.java.p12.model;

/**
 * Class representing one tuple from Polls table.
 * 
 * @author Luka Grgić
 */
public class Poll {
	
	/**id */
	private long id;
	/** title */
	private String title;
	/** message */
	private String message;
	
	/**
	 * @return id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Set id
	 * 
	 * @param id id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set title.
	 * 
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Set message.
	 * 
	 * @param message message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
