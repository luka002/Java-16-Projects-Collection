package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * Singleton class that knows who to return as database service provider.
 * 
 * @author Luka Grgić
 */
public class DAOProvider {

	/**
	 * Object that encapsulates access to database.
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Get object that encapsulates access to database.
	 * 
	 * @return dao object that encapsulates access to database.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}