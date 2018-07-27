package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class that knows who to return as database service provider.
 * 
 * @author Luka Grgić
 */
public class DAOProvider {

	/**
	 * Object that encapsulates access to database.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Get object that encapsulates access to database.
	 * 
	 * @return dao object that encapsulates access to database.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}