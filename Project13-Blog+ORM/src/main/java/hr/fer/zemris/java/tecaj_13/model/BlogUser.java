package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class that represents blog_users table in the database.
 * 
 * @author Luka Grgić
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.checkNick", query="SELECT COUNT(u) FROM BlogUser AS u WHERE u.nick=:nick"),
	@NamedQuery(name="BlogUser.getUsers", query="SELECT u.nick FROM BlogUser AS u"),
	@NamedQuery(name="BlogUser.getUser", query="SELECT u FROM BlogUser AS u WHERE u.nick=:nick")
})
@Entity
@Table(name="blog_users")
public class BlogUser {

	/**
	 * User id
	 */
	private Long id;
	/**
	 * Blog entries that have been created by this user
	 */
	private List<BlogEntry> blogEntries = new ArrayList<>();
	/**
	 * User first name
	 */
	private String firstName;
	/**
	 * User last name
	 */
	private String lastName;
	/**
	 * User nick
	 */
	private String nick;
	/**
	 * User email
	 */
	private String email;
	/**
	 * User password hash
	 */
	private String passwordHash;
	
	/**
	 * Get user id.
	 * 
	 * @return user id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Set user id.
	 * 
	 * @param id user id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Get user blog entries.
	 * 
	 * @return blog entries
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Set blog entries.
	 * 
	 * @param blogEntries blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * Get user first name.
	 * 
	 * @return user first name
	 */
	@Column(length=100, nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Set user first name.
	 * 
	 * @param firstName user first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Get user last name.
	 * 
	 * @return user last name
	 */
	@Column(length=100, nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets user last name.
	 * 
	 * @param lastName user last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Get user nick.
	 * 
	 * @return user nick
	 */
	@Column(length=100, nullable=false, unique=true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Set user nick.
	 * 
	 * @param nick user nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Get user email.
	 * 
	 * @return user email
	 */
	@Column(length=100, nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set user email.
	 * 
	 * @param email user email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Get password hash.
	 * 
	 * @return password hash
	 */
	@Column(length=64, nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Set password hash
	 * 
	 * @param passwordHash password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}