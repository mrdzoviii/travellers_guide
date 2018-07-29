package org.unibl.etf.travelbuddy.mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.travelbuddy.util.ConnectionPool;
import org.unibl.etf.travelbuddy.util.ServiceUtility;


public class UserDao {
	private static final String SQL_SELECT_ACTIVE = "SELECT * FROM user WHERE status=3 AND username=?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM user";
	private static final String SQL_SELECT_BY_USERNAME = "SELECT * FROM user WHERE username= ?";
	private static final String SQL_INSERT = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE user SET status=? WHERE id= ?";
	private static final String SQL_SELECT_REPORT = "SELECT COUNT(*) as count FROM `user` WHERE status!=1 AND create_time LIKE ?";
	
	
	private static final String ID=ServiceUtility.bundle.getString("user.id");
	private static final String NAME=ServiceUtility.bundle.getString("user.name");
	private static final String SURNAME=ServiceUtility.bundle.getString("user.surname");
	private static final String USERNAME=ServiceUtility.bundle.getString("user.username");
	private static final String PASSWORD=ServiceUtility.bundle.getString("user.password");
	private static final String BIRTHDATE=ServiceUtility.bundle.getString("user.birthDate");
	private static final String EMAIL=ServiceUtility.bundle.getString("user.email");
	private static final String STATUS=ServiceUtility.bundle.getString("user.status");
	private static final String TYPE=ServiceUtility.bundle.getString("user.type");
	private static final String CREATE_TIME=ServiceUtility.bundle.getString("user.createTime");
	
	public static List<UserDto> getAllUsers() {
		PreparedStatement ps = null;
		Connection c = null;
		List<UserDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT_ALL);
			rs = ps.executeQuery();
			UserDto user = new UserDto(rs.getInt(ID), rs.getString(USERNAME),rs.getString(PASSWORD),
					 rs.getString(NAME),rs.getString(SURNAME),rs.getDate(BIRTHDATE),rs.getString(EMAIL),rs.getInt(TYPE),rs.getInt(STATUS),rs.getTimestamp(CREATE_TIME));
				result.add(user);
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}
	
	public static int countInPeriod(String filter) {
		PreparedStatement ps = null;
		Connection c = null;
		int result = 0;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { filter };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_REPORT,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getInt("count");
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return result;
	}
	
	public static UserDto selectUsername(String username) {
		PreparedStatement ps = null;
		Connection c = null;
		UserDto user = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { username };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_USERNAME,false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserDto(rs.getInt(ID), rs.getString(USERNAME),rs.getString(PASSWORD),
						 rs.getString(NAME),rs.getString(SURNAME),rs.getDate(BIRTHDATE),rs.getString(EMAIL),rs.getInt(TYPE),rs.getInt(STATUS),rs.getTimestamp(CREATE_TIME));
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return user;
	}
	
	public static UserDto selectActive(String username) {
		PreparedStatement ps = null;
		Connection c = null;
		UserDto user = null;
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { username };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_ACTIVE,false, pom);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new UserDto(rs.getInt(ID), rs.getString(USERNAME),rs.getString(PASSWORD),
						 rs.getString(NAME),rs.getString(SURNAME),rs.getDate(BIRTHDATE),rs.getString(EMAIL),rs.getInt(TYPE),rs.getInt(STATUS),rs.getTimestamp(CREATE_TIME));
			}
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
		return user;
	}
	
	public static void insert(UserDto user) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null, null,user.getUsername(),user.getPassword(),user.getName(),user.getSurname(),
					user.getEmail(),user.getType(),user.getStatus(),user.getBirthDate() };
			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void update(UserDto user) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_UPDATE;
			Object pom[] = { 
					user.getStatus(),
					user.getId()				
			};

			ps = ConnectionPool.prepareStatement(c, query, false, pom);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}

}
