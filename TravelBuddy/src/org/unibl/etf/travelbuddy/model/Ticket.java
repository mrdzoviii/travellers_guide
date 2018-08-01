package org.unibl.etf.travelbuddy.model;

import java.io.Serializable;

public class Ticket implements Serializable{
	private static final long serialVersionUID = -1168216337734283023L;
	private String destination;
	private String transportType;
	private String price;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Ticket(String destination, String transportType, String price) {
		super();
		this.destination = destination;
		this.transportType = transportType;
		this.price = price;
	}
	public Ticket() {
		super();
	}
	
	

}
