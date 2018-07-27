package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Listener that sets up connection with database
 * and initializes it when server is started.
 * 
 * @author Luka Grgić
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties conf = getProperties("/WEB-INF/dbsettings.properties", sce);
		checkDBSettingsContent(conf);
		
		String dbHost = conf.getProperty("host");
		String dbPort = conf.getProperty("port");
		String dbName = conf.getProperty("name");
		String connectionURL = "jdbc:derby://" + dbHost + ":" + dbPort + "/" + dbName;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(conf.getProperty("user"));
		cpds.setPassword(conf.getProperty("password"));
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		try {
			Connection conn = cpds.getConnection();
			
			createAndFillTables(cpds.getConnection(), sce);
			
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error setting up the database", e);
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Creates and fills tables in database if they do not
	 * exist or they are empty.
	 * 
	 * @param connection database connection
	 * @param sce ServletContextEvent
	 * @throws SQLException if error with database
	 */
	private void createAndFillTables(Connection connection, ServletContextEvent sce) throws SQLException {
		Properties properties = getProperties("/WEB-INF/tables.properties", sce);
		
		String polls = properties.getProperty("Polls");
		String createPolls = properties.getProperty("CreatePolls");
		String insertPolls = properties.getProperty("InsertPolls");

		createTableIfNotExists(polls, createPolls, connection);
		fillPoolsTableIfEmpty(polls, insertPolls, connection);

		String pollOptions = properties.getProperty("PollOptions");
		String createPollOptions = properties.getProperty("CreatePollOptions");
		String[] insertPollOptions = new String[] {
				properties.getProperty("InsertBandOptions"),
				properties.getProperty("InsertSongOptions"),
		};

		createTableIfNotExists(pollOptions, createPollOptions, connection);
		fillPoolOptionsTableIfEmpty(pollOptions, insertPollOptions, connection);
	}

	/**
	 * Creates table with provided name if table does not
	 * exist already.
	 * 
	 * @param name table name
	 * @param statement sql create statement
	 * @param con connection
	 * @throws SQLException is error creating the table
	 */
	private void createTableIfNotExists(String name, String statement, Connection con) throws SQLException {
		DatabaseMetaData dbm = con.getMetaData();
		
		try (ResultSet tables = dbm.getTables(null, null, name.toUpperCase(), new String[] {"TABLE"});
				PreparedStatement pst = con.prepareStatement(statement)) {
			if (!tables.next()) {
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			throw new SQLException("Creating table " + name + " error", e);
		}
	}

	/**
	 * Fills Polls table with data if table is empty.
	 * 
	 * @param name table name
	 * @param statement sql insert statement
	 * @param con connection
	 * @throws SQLException if error filling the table
	 */
	private void fillPoolsTableIfEmpty(String name, String statement, Connection con) throws SQLException {
		if (isEmpty(name, con)) return;

		try (PreparedStatement insert = con.prepareStatement(statement)) {
			insert.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Filling Polls table error.", e);
		}
	}

	/**
	 * Checks if table is empty.
	 * 
	 * @param name table name
	 * @param con connection
	 * @return true if table is empty else false
	 * @throws SQLException if database error occurs
	 */
	private boolean isEmpty(String name, Connection con) throws SQLException {
		try (PreparedStatement count = con.prepareStatement("SELECT COUNT(*) AS total FROM " + name);
				ResultSet rs = count.executeQuery()){
			
			rs.next();
			return rs.getInt("total") != 0;
			
		} catch (SQLException e) {
			throw new SQLException("Checking if table empty error.", e);
		}
	}

	/**
	 * Fills PollOptions table with data if table
	 * is empty.
	 * 
	 * @param name table name
	 * @param statement sql insert statement 
	 * @param con connection
	 * @throws SQLException if error filling the table
	 */
	private void fillPoolOptionsTableIfEmpty(String name, String[] statement, Connection con) throws SQLException {
		if (isEmpty(name, con)) return;

		List<Long> pollIDss = getPollIDs(con);

		for (int i = 0; i < statement.length; i++) {
			statement[i] = statement[i].replaceAll("[#]", Long.toString(pollIDss.get(i)));
			
			try (PreparedStatement insert = con.prepareStatement(statement[i])) {							
				insert.executeUpdate();
			} catch (SQLException e) {
				throw new SQLException("Filling PollOptions table error.", e);
			}
			
		}

	}
	
	/**
	 * Getts all id-s from Polls table
	 * 
	 * @param con connection
	 * @return pollID-s list
	 * @throws SQLException if error accessing the database
	 */
	private List<Long> getPollIDs(Connection con) throws SQLException {
		List<Long> pollIDs = new ArrayList<>();
		String statementt = "SELECT id FROM Polls ORDER BY id";
		
		try (PreparedStatement pst = con.prepareStatement(statementt);
				ResultSet rs = pst.executeQuery()) {
			
				while(rs!=null && rs.next()) {
					pollIDs.add(rs.getLong(1));
				}
			
		} catch(SQLException e) {
			throw new SQLException("Getting pollIDs error.", e);
		}
		
		return pollIDs;
	}

	/**
	 * Reads properties file content from given
	 * file name and returns it as Properties class.
	 * 
	 * @param file file path
	 * @param sce ServletContextEvent
	 * @return Properties class from given path
	 */
	private Properties getProperties(String file, ServletContextEvent sce) {
		String fileName = sce.getServletContext().getRealPath(file);
		Path path = Paths.get(fileName);

		if (!Files.isRegularFile(path)) {
			throw new RuntimeException("Properties file missing.");
		}

		Properties properties = new Properties();
		try {
			properties.load(Files.newBufferedReader(path, StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException("Error reading property file.");
		}

		return properties;
	}
	
	/**
	 * Checks if properties in dbsettings.properties are
	 * available.
	 * 
	 * @param conf properties
	 */
	private void checkDBSettingsContent(Properties conf) {
		if (conf.getProperty("host") == null ||
				conf.getProperty("port") == null ||
				conf.getProperty("name") == null ||
				conf.getProperty("user") == null ||
				conf.getProperty("password") == null) {
			throw new RuntimeException("dbsettings.properties file missing property.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}