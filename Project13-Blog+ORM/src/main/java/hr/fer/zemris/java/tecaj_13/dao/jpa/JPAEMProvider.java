package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Class that gets database connections through JPAEMProvider
 * class and puts that connection in ThreadLocal map.
 * Connections are created only when needed.
 * 
 * @author Luka Grgić
 */
public class JPAEMProvider {

	/**
	 * Map that has current thread as key and database connection
	 * as value.
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Gets database connection from <code>locals</code> map
	 * or from <code>JPAEMFProvider</code> if <code>locals</code>
	 * does not have one.
	 * 
	 * @return database connection
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes database connection and removes it from
	 * <code>locals</code> map.
	 * 
	 * @throws DAOException if connection could not be closed.
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}