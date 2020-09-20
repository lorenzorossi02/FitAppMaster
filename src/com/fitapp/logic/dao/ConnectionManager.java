package com.fitapp.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager implements ConnectionInterface {
	/**
	 * This class enables connection to a PostgreSQL database. DAO classes which
	 * uses this connection manager should make queries using prepared statement and
	 * close the connection after operations are ended.
	 * 
	 * example of usage: ConnectionInterface dbIntercace =
	 * ConnectionManager.getInstance(); try (Connection con =
	 * dbInterface.getConnection(); Statement st = dbInterface.openStatement(con);
	 * ResultSet rs = st.executeQuery(*insert a query*)) { LOGGER.info("connecting
	 * to db"); // manage your result } catch (SQLException e){
	 * LOGGER.debug("Connection Error, read the exception message for more info \n
	 * Error Code> {}\nMessage> {}", e.getErrorCode(), e.getMessage()); }
	 * 
	 * @author Andrea Efficace.
	 */

	// Database Properties
	private static final String USR = "postgres";
	private static final String PWD = "postgres";
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
	private static final int[] portNumber = { 5432 };
	private static final String DBNAME = "fitappdb";
	private static final int SCROLL_TYPE = ResultSet.TYPE_SCROLL_INSENSITIVE;
	private static final int READ_TYPE = ResultSet.CONCUR_READ_ONLY;
	private static final PGConnectionPoolDataSource connectionPool = new PGConnectionPoolDataSource();
	protected Statement statement;
	// end of properties.

	// private instance to implement Singleton Pattern
	private static ConnectionManager instance;
	// connection attribute
	private Connection connection;

	protected ConnectionManager() {
		// constructor
		// set up data connection for postgreSQL DB
		connectionPool.setPortNumbers(portNumber);
		connectionPool.setDatabaseName(DBNAME);
		connectionPool.setUser(USR);
		connectionPool.setPassword(PWD);
		// set connection attribute to null
		connection = null;
		statement = null;
		try {
			// create connection
			connection = connectionPool.getConnection();
			statement = connection.createStatement(SCROLL_TYPE, READ_TYPE);
		} catch (SQLException e) {
			// logger.log(Level.SEVERE, e.getMessage(), e);
			LOGGER.info("NO CONNECTION");
		}
	}

	// singleton instance to prevent multiple connections

	public static synchronized ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	/**
	 * This method returns the connection object, needed to interact with the
	 * database. The connection object is retrieve from the PostgreSQL
	 * connectionPool.
	 * 
	 * @return the connection object.
	 */

	@Override
	public Connection getConnection() throws SQLException {
		// checks if the connection is open
		if (checkConnectionStatus(this.connection)) {
			// there is still an open connection, no connection object has to be created.
			LOGGER.info("Connection Established to {} with connection obj: {}", DBNAME, this.connection);
		} else {
			// at initialization point, this.connection is null and checkConnectionStatus
			// returns false
			LOGGER.info("Connection closed, asking to the connection pool for a new connection object");
			// get a connection object from the connection pool.
			this.connection = connectionPool.getConnection();
			LOGGER.info("Connection Established to {} with connection obj: {}", DBNAME, this.connection);
		}
		return this.connection;
	}

	/**
	 * This method opens a statement with previously defined properties. This method
	 * should be used in a try-with-resource statement, in order to automatically
	 * close the statement.
	 * 
	 * @param connection the connection object that has to create the statement.
	 * @return statement the statement object.
	 */
	@Override
	public Statement openStatement(Connection connection) throws SQLException {
		return connection.createStatement(SCROLL_TYPE, READ_TYPE);
	}

	/**
	 * This method opens a prepared statement with previously defined query. This
	 * method should be used in a try-with-resource statement, in order to
	 * automatically close the statement.
	 * 
	 * @param connection the connection object that has to create the statement.
	 * @param sql        the prepared SQL query that has to be executed.
	 * @return statement the statement object.
	 */
	@Override
	public PreparedStatement openPreparedStatement(Connection connection, String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	/**
	 * This method closes the connection object passed by input. This method is
	 * useless if try-with-resource is used to make queries.
	 * 
	 * @param connection the connection statement object that has to be closed.
	 */

	@Override
	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				LOGGER.info("Connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method closes the prepared statement object passed by input. This method
	 * is useless if try-with-resource is used to make queries.
	 * 
	 * @param preparedStatement the prepared statement object that has to be closed.
	 */

	@Override
	public void closeStatement(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
				LOGGER.info("statement closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method closes the statement object passed by input. This method is
	 * useless if try-with-resource is used to make queries.
	 * 
	 * @param statement the statement object that has to be closed.
	 */
	@Override
	public void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
				LOGGER.info("statement closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method makes a SELECT VERSION() query to check if the connection to the
	 * database is still open.
	 * 
	 * @param connection the connection object that need to be checked.
	 * @return true if the connection is still open, otherwise false. If the
	 *         connection object is null, the method returns false anyway.
	 */

	@Override
	public boolean checkConnectionStatus(Connection connection) {
		if (connection != null) {
			String sql = "SELECT VERSION();";
			// the try-with-resource guarantees that the prepared statement is automatically
			// closed
			try (PreparedStatement pst = openPreparedStatement(connection, sql)) {
				pst.executeQuery();
				return true;
			} catch (SQLException e) {
				LOGGER.info("Connection closed");
			}
		}
		return false;
	}

	public boolean checkColumnResultValidity(int n, ResultSet rs) throws SQLException {
		ResultSetMetaData rsMeta = rs.getMetaData();
		return rsMeta.getColumnCount() == n;
	}

	public boolean checkRowResultValidity(int n, ResultSet rs) throws SQLException {
		rs.first();
		int i = 0;
		do {
			i++;
		} while (rs.next());
		rs.first();
		return i == n;
	}

	public boolean checkResultValidity(int row, int col, ResultSet rs) throws SQLException {

		return checkColumnResultValidity(col, rs) && checkRowResultValidity(row, rs);
	}

	public boolean checkMinRow(int minRow, ResultSet rs) throws SQLException {
		rs.beforeFirst();
		int i = 0;
		while (rs.next()) {
			i++;
		}
		rs.first();
		return minRow <= i;
	}
}