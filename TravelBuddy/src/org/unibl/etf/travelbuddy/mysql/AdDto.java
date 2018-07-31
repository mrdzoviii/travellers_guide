package org.unibl.etf.travelbuddy.mysql;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

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
	private Double locationFromLatitude;
	private Double locationFromLongitude;
	private Double locationToLatitude;
	private Double locationToLongitude;
	private int status;
	private int userId;
	private UserDto user;
	private boolean from;
	private boolean to;
	private String centerFrom;
	private String centerTo;
	private MapModel mapFrom;
	private MapModel mapTo;
	
	
	
	public String getCenterFrom() {
		return centerFrom;
	}
	public void setCenterFrom(String centerFrom) {
		this.centerFrom = centerFrom;
	}
	public String getCenterTo() {
		return centerTo;
	}
	public void setCenterTo(String centerTo) {
		this.centerTo = centerTo;
	}
	public void updateFrom() {
		mapFrom=new DefaultMapModel();
		LatLng cord=new LatLng(locationFromLatitude, locationFromLongitude);
		mapFrom.addOverlay(new Marker(cord));
		centerFrom=cord.getLat()+","+cord.getLng();
	}
	public void updateTo() {
		mapTo=new DefaultMapModel();
		LatLng cord=new LatLng(locationToLatitude, locationToLongitude);
		mapTo.addOverlay(new Marker(cord));
		centerTo=cord.getLat()+","+cord.getLng();
	}
	
	public MapModel getMapFrom() {
		return mapFrom;
	}
	public void setMapFrom(MapModel mapFrom) {
		this.mapFrom = mapFrom;
	}
	public MapModel getMapTo() {
		return mapTo;
	}
	public void setMapTo(MapModel mapTo) {
		this.mapTo = mapTo;
	}
	public boolean isFrom() {
		return from;
	}
	public void setFrom(boolean from) {
		this.from = from;
	}
	public boolean isTo() {
		return to;
	}
	public void setTo(boolean to) {
		this.to = to;
	}
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
	
	public Double getLocationFromLatitude() {
		return locationFromLatitude;
	}
	public void setLocationFromLatitude(Double locationFromLatitude) {
		this.locationFromLatitude = locationFromLatitude;
	}
	public Double getLocationFromLongitude() {
		return locationFromLongitude;
	}
	public void setLocationFromLongitude(Double locationFromLongitude) {
		this.locationFromLongitude = locationFromLongitude;
	}
	public Double getLocationToLatitude() {
		return locationToLatitude;
	}
	public void setLocationToLatitude(Double locationToLatitude) {
		this.locationToLatitude = locationToLatitude;
	}
	public Double getLocationToLongitude() {
		return locationToLongitude;
	}
	public void setLocationToLongitude(Double locationToLongitude) {
		this.locationToLongitude = locationToLongitude;
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
			String destination, int numberOfPersons, Double locationFromLatitude, Double locationFromLongitude,
			Double locationToLatitude, Double locationToLongitude, int status, int userId, UserDto user, boolean from,
			boolean to) {
		super();
		this.id = id;
		this.category = category;
		this.createTime = createTime;
		this.title = title;
		this.departureTime = departureTime;
		this.startingPoint = startingPoint;
		this.destination = destination;
		this.numberOfPersons = numberOfPersons;
		this.locationFromLatitude = locationFromLatitude;
		this.locationFromLongitude = locationFromLongitude;
		this.locationToLatitude = locationToLatitude;
		this.locationToLongitude = locationToLongitude;
		this.status = status;
		this.userId = userId;
		this.user = user;
		this.from = from;
		this.to = to;
		if(to) {
			updateTo();
		}
		if(from) {
			updateFrom();
		}
	}
	@Override
	public String toString() {
		return "AdDto [id=" + id + ", category=" + category + ", createTime=" + createTime + ", title=" + title
				+ ", departureTime=" + departureTime + ", startingPoint=" + startingPoint + ", destination="
				+ destination + ", numberOfPersons=" + numberOfPersons + ", locationFromLatitude="
				+ locationFromLatitude + ", locationFromLongitude=" + locationFromLongitude + ", locationToLatitude="
				+ locationToLatitude + ", locationToLongitude=" + locationToLongitude + ", status=" + status
				+ ", userId=" + userId + ", user=" + user + ", from=" + from + ", to=" + to + "]";
	}
	
	
	
	
	
	
	
	
	
	

	
}
