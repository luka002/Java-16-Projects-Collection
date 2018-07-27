package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represents blog_entries table in the database.
 * 
 * @author Luka Grgić
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.getBlogs",query="SELECT b FROM BlogEntry AS b WHERE b.creator.nick=:nick")
})
@Entity
@Table(name="blog_entries")
//@Cacheable(true)
public class BlogEntry {

	/**
	 * Blog entry id
	 */
	private Long id;
	/**
	 * User that created this blog
	 */
	private BlogUser creator;
	/**
	 * Comment written on this blog
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Time when entry has been created
	 */
	private Date createdAt;
	/**
	 * Time when entry has been last modified
	 */
	private Date lastModifiedAt;
	/**
	 * Title of the blog
	 */
	private String title;
	/**
	 * Content of the blog
	 */
	private String text;
	
	/**
	 * Get blog id.
	 * 
	 * @return blog id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Set blog id.
	 * 
	 * @param id blog id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Get creator of the blog.
	 * 
	 * @return creator of the blog
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Set creator of the blog.
	 * 
	 * @param creator creator of the blog
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Get blogs comments.
	 * 
	 * @return blogs comments
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Set blogs comments.
	 * 
	 * @param comments blogs comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Get creation time.
	 * 
	 * @return creation time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Set creation time.
	 * 
	 * @param createdAt creation time
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Get last modification time.
	 * 
	 * @return last modification time
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Set last modification time.
	 * 
	 * @param lastModifiedAt last modification time
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Get blogs title.
	 * 
	 * @return blogs title
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * Set blogs title.
	 * 
	 * @param title blogs title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get blogs content.
	 * 
	 * @return blogs content
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Set blogs content.
	 * 
	 * @param text blogs content
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}