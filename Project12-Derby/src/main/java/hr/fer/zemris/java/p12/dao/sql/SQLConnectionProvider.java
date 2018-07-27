package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Storage of connection towards database in ThreadLocal object.
 *
 * @author Luka Grgić
 */
public class SQLConnectionProvider {

	/**
	 * Storage of connection towards database
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Set connection for current thread or remove if argument is null.
	 * 
	 * @param con database connection
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Get connection that current thread can use.
	 * 
	 * @return connection towards database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}