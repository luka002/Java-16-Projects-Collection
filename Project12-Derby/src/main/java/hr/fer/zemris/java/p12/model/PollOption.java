package hr.fer.zemris.java.p12.model;

/**
 * Class representing one tuple from PollOptions table.
 * 
 * @author Luka Grgić
 */
public class PollOption {
	
	/** id */
	private long id;
	/** title */
	private String optionTitle;
	/** link */
	private String optionLink;
	/** poll id */
	private long pollID;
	/** votes count */
	private long votesCount;
	
	/**
	 * @return id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Set id.
	 * 
	 * @param id id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}
	
	/**
	 * Set title
	 * 
	 * @param optionTitle title
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	/**
	 * @return link
	 */
	public String getOptionLink() {
		return optionLink;
	}
	
	/**
	 * Set link.
	 * 
	 * @param optionLink link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}
	
	/**
	 * @return poll id
	 */
	public long getPollID() {
		return pollID;
	}
	
	/**
	 * Set poll id.
	 * 
	 * @param pollID poll id
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
	
	/**
	 * @return votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
	/**
	 * Set votes count.
	 * 
	 * @param votesCount votes count.
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
	
}
