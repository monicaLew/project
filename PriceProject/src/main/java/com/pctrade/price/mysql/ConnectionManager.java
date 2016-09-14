package com.pctrade.price.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.pctrade.price.utils.ConfigUtils;

public class ConnectionManager {

	private String dbDriver;
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private static class HolderManager {
		private final static ConnectionManager connectionManager = new ConnectionManager();
	}

	private ConnectionManager() {
		Properties dbProperties = ConfigUtils.loadDbProperties();
		dbDriver = dbProperties.getProperty("mysql.db_driver");
		dbUrl = dbProperties.getProperty("mysql.db_url");
		dbUser = dbProperties.getProperty("mysql.db_user");
		dbPassword = dbProperties.getProperty("mysql.db_password");
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static ConnectionManager getManager() {
		return HolderManager.connectionManager;
	}

	private List<Connection> connectionList = new ArrayList<Connection>();

	public Connection getConnection() {
		if (connectionList.isEmpty()) {
			try {
				return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return connectionList.remove(0);
	}

	public void close(Connection connection) {
		connectionList.add(connection);
	}

	public void closeDbResources(Connection connection, PreparedStatement preparedStatement) {
		closeDbResources(connection, preparedStatement, null);
	}

	public void closeDbResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(preparedStatement);
		closeConnection(connection);
	}

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can not close Connection");
			}
		}
	}

	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			System.out.println("Can not close Statement");
		}
	}

	public void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			System.out.println("Can not close ResultSet");
		}
	}
}