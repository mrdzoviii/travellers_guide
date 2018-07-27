package org.unibl.etf.traveladvertiser.mysql;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.traveladvertiser.util.ConnectionPool;


public class AdDao {
	private static final String SQL_SELECT = "SELECT * FROM ad WHERE ad_date_from <= date(now()) AND ad_date_to >= date(now())";
	private static final String SQL_INSERT = "INSERT INTO ad VALUES (?, ?, ?, ?, ?)";
	private static final String AD_ID=ConnectionPool.bundle.getString("ad.id");
	private static final String AD_TEXT=ConnectionPool.bundle.getString("ad.text");
	private static final String AD_IMAGE=ConnectionPool.bundle.getString("ad.image");
	private static final String AD_DATE_FROM=ConnectionPool.bundle.getString("ad.dateFrom");
	private static final String AD_DATE_TO=ConnectionPool.bundle.getString("ad.dateTo");
	
	public static List<AdDto> getAds() {
		PreparedStatement ps = null;
		Connection c = null;
		List<AdDto> result = new ArrayList<>();
		ResultSet rs = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			ps = c.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			while (rs.next()) {
				InputStream is = rs.getBinaryStream(AD_IMAGE);
				byte[] bytes = null;
				if(is != null) {
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					int nRead;
					byte[] data = new byte[1024*1024];
					while ((nRead = is.read(data, 0, data.length)) != -1) {
						buffer.write(data, 0, nRead);
					}
					buffer.flush();
					bytes = buffer.toByteArray();
				}
			    AdDto ad = new AdDto(rs.getInt(AD_ID), rs.getString(AD_TEXT),
						bytes, rs.getDate(AD_DATE_FROM), rs.getDate(AD_DATE_TO));
				result.add(ad);
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

	public static void insertAd(AdDto ad) {
		PreparedStatement ps = null;
		Connection c = null;
		try {
			c = ConnectionPool.getInstance().checkOut();
			String query = SQL_INSERT;
			Object pom[] = { null, ad.getDateFrom(),ad.getDateTo(),ad.getText(),ad.getImage() };
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
