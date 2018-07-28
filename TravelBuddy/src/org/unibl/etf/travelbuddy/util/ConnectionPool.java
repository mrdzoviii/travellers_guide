package org.unibl.etf.travelbuddy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ConnectionPool {
	private String jdbcURL;
	private String username;
	private String password;
	private String driver;
	private int preconnectCount;
	private int maxIdleConnections;
	private int maxConnections;
	private int connectCount;
	private List<Connection> usedConnections;
	private List<Connection> freeConnections;


	private static ConnectionPool instance;

	public static ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}

	private ConnectionPool() {
		readConfiguration();
		try {
			freeConnections = new ArrayList<Connection>();
			usedConnections = new ArrayList<Connection>();

			for (int i = 0; i < preconnectCount; i++) {
				Connection conn = DriverManager.getConnection(jdbcURL, username, password);
				freeConnections.add(conn);
			}
			connectCount = preconnectCount;
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

	private void readConfiguration() {
		try {
			jdbcURL = ServiceUtility.bundle.getString("jdbcURL");
			username = ServiceUtility.bundle.getString("username");
			password = ServiceUtility.bundle.getString("password");
			driver = ServiceUtility.bundle.getString("driver");
			Class.forName(driver);
			preconnectCount = 0;
			maxIdleConnections = 10;
			maxConnections = 10;
			preconnectCount = Integer.parseInt(ServiceUtility.bundle.getString("preconnectCount"));
			maxIdleConnections = Integer.parseInt(ServiceUtility.bundle.getString("maxIdleConnections"));
			maxConnections = Integer.parseInt(ServiceUtility.bundle.getString("maxConnections"));
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}

	public synchronized Connection checkOut() throws SQLException {
		Connection conn = null;
		if (freeConnections.size() > 0) {
			conn = freeConnections.remove(0);
			usedConnections.add(conn);
		} else {
			if (connectCount < maxConnections) {
				conn = DriverManager.getConnection(jdbcURL, username, password);
				usedConnections.add(conn);
				connectCount++;
			} else {
				try {
					wait();
					conn = freeConnections.remove(0);
					usedConnections.add(conn);
				} catch (InterruptedException e) {
					 e.printStackTrace();
				}
			}
		}
		return conn;
	}

	public synchronized void checkIn(Connection conn) {
		if (conn == null)
			return;
		if (usedConnections.remove(conn)) {
			freeConnections.add(conn);
			while (freeConnections.size() > maxIdleConnections) {
				int lastOne = freeConnections.size() - 1;
				Connection c = freeConnections.remove(lastOne);
				try {
					c.close();
				} catch (SQLException e) {
					 e.printStackTrace();
				}
			}
			notify();
		}
	}

	public static PreparedStatement prepareStatement(Connection c, String sql, boolean retGenKeys, Object... values)
			throws SQLException {
		PreparedStatement ps = c.prepareStatement(sql,
				retGenKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < values.length; i++)
			ps.setObject(i + 1, values[i]);
		return ps;
	}

	public static void close(Statement s) {
		if (s != null)
			try {
				s.close();
			} catch (SQLException e) {
				 e.printStackTrace();
			}
	}

	public static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				 e.printStackTrace();
			}
	}

	public static void close(ResultSet rs, Statement s) {
		close(rs);
		close(s);
	}

}
