package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface for accessing data from the database.
 * 
 * @author Luka Grgić
 */
public interface DAO {

	/**
	 * Gets entry with provided <code>id</code>. If one doesn't exist returns
	 * <code>null</code>.
	 * 
	 * @param id entry key
	 * @return entry or <code>null</code> if entry doesn't exist
	 * @throws DAOException if error occurs while accessing data
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Checks is BlogUser with provided <code>nick</code> exists.
	 * Returns <code>true</code> if it does or <code>false</code>
	 * if it doesn't.
	 * 
	 * @param nick user nick
	 * @return <code>true</code> if nick is found otherwise <code>false</code>.
	 * @throws DAOException if error occurs while accessing data
	 */
	boolean nickExists(String nick) throws DAOException;
	
	/**
	 * Gets nick's from all users in the database.
	 * 
	 * @return nick's from all users in the database or <code>null</code>
	 * if there are no users.
	 * @throws DAOException if error occurs while accessing data
	 */
	List<String> getUsersNicks() throws DAOException;
	
	/**
	 * Store provided user in the database.
	 * 
	 * @param user user that will be stored
	 * @throws DAOException if error occurs while accessing data
	 */
	void saveUser(BlogUser user) throws DAOException;
	
	/**
	 * Get user that has nick same as provided one.
	 * 
	 * @param nick nick
	 * @return user that has nick same as provided one or <code>null</code>
	 * it no such user exists.
	 * @throws DAOException if error occurs while accessing data
	 */
	BlogUser getUser(String nick) throws DAOException;
	
	/**
	 * Get all blogs from user that has same nick as provided one.
	 * 
	 * @param nick nick
	 * @return list of all blogs from specified user or <code>null</code>
	 * if no entries are in the database.
	 * @throws DAOException if error occurs while accessing data
	 */
	List<BlogEntry> getUserBlogs(String nick) throws DAOException;
	
	/**
	 * Store provided <code>BlogEntry</code> in the database.
	 * 
	 * @param entry <code>BlogEntry</code> to be stored.
	 * @throws DAOException if error occurs while accessing data
	 */
	void saveBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Update <code>BlogEntry</code> with value from <code>entry</code>. 
	 * 
	 * @param entry new <code>BlogEntry</code> value.
	 * @throws DAOException if error occurs while accessing data.
	 */
	void updateBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Store provided <code>BlogComment</code> in the database.
	 * 
	 * @param comment <code>BlogComment</code> to be stored.
	 * @throws DAOException if error occurs while accessing data
	 */
	void saveComment(BlogComment comment) throws DAOException;
	
}