package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represents blog_coments table in the database.
 * 
 * @author Luka Grgić
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Comment id
	 */
	private Long id;
	/**
	 * Blog entry that this comment belongs to
	 */
	private BlogEntry blogEntry;
	/**
	 * Email from user that has written it
	 */
	private String usersEMail;
	/**
	 * Content of the comment
	 */
	private String message;
	/**
	 * Time when the comment has been posted
	 */
	private Date postedOn;
	
	/**
	 * Gets comment id.
	 * 
	 * @return comment id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets comment id.
	 * @param id comment id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * Gets blog entry.
	 * 
	 * @return blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets blog entry.
	 * 
	 * @param blogEntry blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Gets user email.
	 * 
	 * @return user email
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets user email.
	 * 
	 * @param usersEMail user email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets comments content.
	 * 
	 * @return comments content
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets comments content.
	 * 
	 * @param message comments content
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets time when comment has been posted.
	 * 
	 * @return time when comment has been posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets time when comment has been posted.
	 * 
	 * @param postedOn time when comment has been posted
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}