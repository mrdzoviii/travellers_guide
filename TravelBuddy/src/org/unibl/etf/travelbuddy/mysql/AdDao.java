package org.unibl.etf.travelbuddy.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.travelbuddy.util.ConnectionPool;
import org.unibl.etf.travelbuddy.util.ServiceUtility;

public class AdDao {

	private static final String ID=ServiceUtility.bundle.getString("ad.id");
	private static final String CREATE_TIME=ServiceUtility.bundle.getString("ad.createTime");
	private static final String CATEGORY=ServiceUtility.bundle.getString("ad.category");
	private static final String TITLE=ServiceUtility.bundle.getString("ad.title");
	private static final String DEPARTURE_TIME=ServiceUtility.bundle.getString("ad.departureTime");
	private static final String STARTING_POINT=ServiceUtility.bundle.getString("ad.startingPoint");
	private static final String DESTINATION=ServiceUtility.bundle.getString("ad.destination");
	private static final String NUMBER_OF_PERSONS=ServiceUtility.bundle.getString("ad.numberOfPersons");
	private static final String GOOGLE_MAP_STARTING_POINT=ServiceUtility.bundle.getString("ad.googleMapStartingPoint");
	private static final String GOOGLE_MAP_DESTINATION=ServiceUtility.bundle.getString("ad.googleMapDestination");
	private static final String GOOGLE_MAP_LOCATION=ServiceUtility.bundle.getString("ad.googleMapLocation");
	private static final String STATUS=ServiceUtility.bundle.getString("ad.status");
	private static final String USER_ID=ServiceUtility.bundle.getString("ad.userId");
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM advertisement WHERE status=1";
	private static final String SQL_SELECT_STATUS = "SELECT * FROM advertisement WHERE status=?";
	private static final String SQL_SELECT_BY_USER_ID = "SELECT * FROM advertisement WHERE user_id=? and status=1";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM advertisement WHERE id=? and status=1";
	private static final String SQL_DELETE = "UPDATE advertisement SET status=0 WHERE id=?";
	private static final String SQL_INSERT = "INSERT INTO advertisement VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE advertisement SET title=?, departure_time=?, starting_point=?, category=?, destination=?, number_of_persons=? ,google_map_starting_point=?, google_map_destination=?, google_map_location=?  WHERE id=?";
	private static final String SQL_UPDATE_STATUS = "UPDATE advertisement SET status=? WHERE id=?";
	private static final String SQL_SELECT_DISTINCT = "SELECT DISTINCT a.* FROM advertisement a INNER JOIN content_report c on a.id=c.advertisment_id WHERE a.status=1 AND c.status=1";
	private static final String SQL_SELECT_REPORT = "SELECT COUNT(*) as count FROM `advertisement` WHERE create_time LIKE ?";
	
	public static void updateStatus(int id, int status) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { status, id };
			ps = ConnectionPool.prepareStatement(c,SQL_UPDATE_STATUS,false, pom);
			ps.executeUpdate();
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void update(AdDto ad) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { 
					ad.getTitle(), 
					ad.getDepartureTime(),
					ad.getStartingPoint(),
					ad.getCategory(),
					ad.getDestination(),
					ad.getNumberOfPersons(),
					ad.getGoogleMapStartingPoint(),
					ad.getGoogleMapDestination(),
					ad.getGoogleMapLocation(),
					ad.getId() };
			ps = ConnectionPool.prepareStatement(c,SQL_UPDATE,false, pom);
			ps.executeUpdate();
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static void insert(AdDto ad) {
		PreparedStatement ps = null;
		Connection c = null;

		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { 
					null,// id
					null,// create_time
					ad.getTitle(), 
					ad.getDepartureTime(),
					ad.getStartingPoint(),
					ad.getCategory(),
					ad.getDestination(),
					ad.getNumberOfPersons(),
					ad.getGoogleMapStartingPoint(),
					ad.getGoogleMapDestination(),
					ad.getGoogleMapLocation(),
					ad.getStatus(),
					ad.getUserId()
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
	
	public static void delete(int id) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { id };
			ps = ConnectionPool.prepareStatement(c,SQL_DELETE,false, pom);
			ps.executeUpdate();
			ps.close();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
			ConnectionPool.getInstance().checkIn(c);
		}
	}
	
	public static List<AdDto> selectAll() {
		PreparedStatement ps = null;
		Connection c = null;
		List<AdDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT_ALL);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdDto advertisment = new AdDto(rs.getInt(ID), rs.getInt(CATEGORY),rs.getDate(CREATE_TIME),rs.getString(TITLE),rs.getDate(DEPARTURE_TIME),
						rs.getString(STARTING_POINT),rs.getString(DESTINATION),rs.getInt(NUMBER_OF_PERSONS),rs.getString(GOOGLE_MAP_STARTING_POINT), rs.getString(GOOGLE_MAP_DESTINATION),rs.getInt(GOOGLE_MAP_LOCATION), rs.getInt(STATUS),rs.getInt(USER_ID),null);
				result.add(advertisment);
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
	
	public static List<AdDto> selectByStatus(int status) {
		PreparedStatement ps = null;
		Connection c = null;
		List<AdDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { status };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_STATUS,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdDto advertisment = new AdDto(rs.getInt(ID), rs.getInt(CATEGORY),rs.getDate(CREATE_TIME),rs.getString(TITLE),rs.getDate(DEPARTURE_TIME),
						rs.getString(STARTING_POINT),rs.getString(DESTINATION),rs.getInt(NUMBER_OF_PERSONS),rs.getString(GOOGLE_MAP_STARTING_POINT), rs.getString(GOOGLE_MAP_DESTINATION),rs.getInt(GOOGLE_MAP_LOCATION), rs.getInt(STATUS),rs.getInt(USER_ID),null);
				result.add(advertisment);
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
	
	public static List<AdDto> selectByUserId(int userId) {
		PreparedStatement ps = null;
		Connection c = null;
		List<AdDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { userId };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_USER_ID,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdDto advertisment = new AdDto(rs.getInt(ID), rs.getInt(CATEGORY),rs.getDate(CREATE_TIME),rs.getString(TITLE),rs.getDate(DEPARTURE_TIME),
						rs.getString(STARTING_POINT),rs.getString(DESTINATION),rs.getInt(NUMBER_OF_PERSONS),rs.getString(GOOGLE_MAP_STARTING_POINT), rs.getString(GOOGLE_MAP_DESTINATION),rs.getInt(GOOGLE_MAP_LOCATION), rs.getInt(STATUS),rs.getInt(USER_ID),null);
				result.add(advertisment);
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
	
	public static List<AdDto> selectById(int id) {
		PreparedStatement ps = null;
		Connection c = null;
		List<AdDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			Object pom[] = { id };
			ps = ConnectionPool.prepareStatement(c,SQL_SELECT_BY_ID,false, pom);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdDto advertisment = new AdDto(rs.getInt(ID), rs.getInt(CATEGORY),rs.getDate(CREATE_TIME),rs.getString(TITLE),rs.getDate(DEPARTURE_TIME),
						rs.getString(STARTING_POINT),rs.getString(DESTINATION),rs.getInt(NUMBER_OF_PERSONS),rs.getString(GOOGLE_MAP_STARTING_POINT), rs.getString(GOOGLE_MAP_DESTINATION),rs.getInt(GOOGLE_MAP_LOCATION), rs.getInt(STATUS),rs.getInt(USER_ID),null);
				result.add(advertisment);
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
	
	
	public static List<AdDto> selectReported() {
		PreparedStatement ps = null;
		Connection c = null;
		List<AdDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT_DISTINCT);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdDto advertisment = new AdDto(rs.getInt(ID), rs.getInt(CATEGORY),rs.getDate(CREATE_TIME),rs.getString(TITLE),rs.getDate(DEPARTURE_TIME),
						rs.getString(STARTING_POINT),rs.getString(DESTINATION),rs.getInt(NUMBER_OF_PERSONS),rs.getString(GOOGLE_MAP_STARTING_POINT), rs.getString(GOOGLE_MAP_DESTINATION),rs.getInt(GOOGLE_MAP_LOCATION), rs.getInt(STATUS),rs.getInt(USER_ID),null);
				result.add(advertisment);
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
	


}
