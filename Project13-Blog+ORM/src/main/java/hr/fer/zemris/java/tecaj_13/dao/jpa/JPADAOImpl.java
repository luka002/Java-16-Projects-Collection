package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Concrete implementation of DAO interface.
 *  
 * @author Luka Grgić
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public boolean nickExists(String nick) throws DAOException {
		long count = JPAEMProvider.getEntityManager()
									.createNamedQuery("BlogUser.checkNick", Long.class)
									.setParameter("nick", nick)
									.getSingleResult();
		return count > 0;
	}

	@Override
	public List<String> getUsersNicks() throws DAOException {
		return JPAEMProvider.getEntityManager()
							.createNamedQuery("BlogUser.getUsers", String.class)
							.getResultList();
	}

	@Override
	public void saveUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public BlogUser getUser(String nick) {
		return JPAEMProvider.getEntityManager()
							.createNamedQuery("BlogUser.getUser", BlogUser.class)
							.setParameter("nick", nick)
							.getSingleResult();
	}

	@Override
	public List<BlogEntry> getUserBlogs(String nick) {
		return JPAEMProvider.getEntityManager()
							.createNamedQuery("BlogEntry.getBlogs", BlogEntry.class)
							.setParameter("nick", nick)
							.getResultList();
	}

	@Override
	public void saveBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}
	
	@Override
	public void updateBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().merge(entry);
	}
	
	@Override
	public void saveComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}
	
}