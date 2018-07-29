package org.unibl.etf.travelbuddy.mysql;

import java.io.Serializable;
import java.util.Date;

public class AdDto implements Serializable {
	private static final long serialVersionUID = 6705790616384928976L;
	private int id;
	private int category;
	private Date createTime;
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	private Date departureTime;
	private String startingPoint;
	private String destination;
	private int numberOfPersons;
	private String googleMapStartingPoint;
	private String googleMapDestination;
	private int googleMapLocation;
	private int status;
	private int userId;
	private UserDto user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(String startingPoint) {
		this.startingPoint = startingPoint;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getNumberOfPersons() {
		return numberOfPersons;
	}
	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}
	public String getGoogleMapStartingPoint() {
		return googleMapStartingPoint;
	}
	public void setGoogleMapStartingPoint(String googleMapStartingPoint) {
		this.googleMapStartingPoint = googleMapStartingPoint;
	}
	public String getGoogleMapDestination() {
		return googleMapDestination;
	}
	public void setGoogleMapDestination(String googleMapDestination) {
		this.googleMapDestination = googleMapDestination;
	}
	public int getGoogleMapLocation() {
		return googleMapLocation;
	}
	public void setGoogleMapLocation(int googleMapLocation) {
		this.googleMapLocation = googleMapLocation;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public AdDto() {
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UserDto getUser() {
		if(user==null) {
			user=UserDao.selectById(userId);
		}
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public AdDto(int id, int category, Date createTime, String title, Date departureTime, String startingPoint,
			String destination, int numberOfPersons, String googleMapStartingPoint, String googleMapDestination,
			int googleMapLocation, int status, int userId, UserDto user) {
		super();
		this.id = id;
		this.category = category;
		this.createTime = createTime;
		this.title = title;
		this.departureTime = departureTime;
		this.startingPoint = startingPoint;
		this.destination = destination;
		this.numberOfPersons = numberOfPersons;
		this.googleMapStartingPoint = googleMapStartingPoint;
		this.googleMapDestination = googleMapDestination;
		this.googleMapLocation = googleMapLocation;
		this.status = status;
		this.userId = userId;
		this.user = user;
	}
	
	
	
	
	
	

	
}
