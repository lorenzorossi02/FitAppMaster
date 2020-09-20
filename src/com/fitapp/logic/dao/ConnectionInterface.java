package com.fitapp.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionInterface {
	/**
	 * This interface declares method to establish connections to database. This
	 * methods should be used in try-with-resource, in order to not call the 'close'
	 * methods and automatically close Connection, Statement and, eventually
	 * ResultSet, objects 
	 * 
	 * @author Andrea Efficace.
	 */

	public Connection getConnection() throws SQLException;

	public PreparedStatement openPreparedStatement(Connection connection, String sql) throws SQLException;

	public Statement openStatement(Connection connection) throws SQLException;

	public void closeConnection(Connection connection);

	public void closeStatement(PreparedStatement preparedStatement);

	public void closeStatement(Statement statement);

	public boolean checkConnectionStatus(Connection connection);
}
