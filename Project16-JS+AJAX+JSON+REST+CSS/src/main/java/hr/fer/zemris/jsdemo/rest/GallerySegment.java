package hr.fer.zemris.jsdemo.rest;

import java.util.Set;

/**
 * Class that represents one picture from /WEB-INF/slike folder.
 * It contains pictures name, title and tags.
 * 
 * @author Luka Grgić
 */
public class GallerySegment {
	
	/**
	 * Picture name
	 */
	private String name;
	/**
	 * Picture title 
	 */
	private String title;
	/**
	 * Picture tags
	 */
	private Set<String> tags;

	/**
	 * Constructor.
	 * 
	 * @param name picture name
	 * @param title picture title
	 * @param tags picture tags
	 */
	public GallerySegment(String name, String title, Set<String> tags) {
		this.name = name;
		this.title = title;
		this.tags = tags;
	}

	/**
	 * Gets picture name.
	 * 
	 * @return picture name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets picture name.
	 * 
	 * @param name picture name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets picture title.
	 * 
	 * @return picture title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets picture title.
	 * 
	 * @param title picture title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get picture tags.
	 * 
	 * @return picture tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * Set picture tags.
	 * 
	 * @param tags picture tags
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

}
