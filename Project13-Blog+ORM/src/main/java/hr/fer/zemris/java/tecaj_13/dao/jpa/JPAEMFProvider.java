package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Stores reference to <code>EntityManagerFactory</code>
 * which can be accessed through static method.
 * 
 * @author Luka Grgić
 */
public class JPAEMFProvider {

	/**
	 * Creates connections with database.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Gets database connections creator.
	 * 
	 * @return database connections creator
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets database connections creator.
	 * 
	 * @param emf database connections creator
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}