package org.unibl.etf.traveltickets.db.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ticket implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4325665520240207287L;
	
	public static final String BUS="BUS";
	public static final String TRAIN="TRAIN";
	public static final String AIRPLANE="AIRPLANE";
	
	
	private String destination;
	private String typeOfTransport;
	private String price;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTypeOfTransport() {
		return typeOfTransport;
	}
	public void setTypeOfTransport(String typeOfTransport) {
		this.typeOfTransport = typeOfTransport;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Ticket(String destination, String typeOfTransport, String price) {
		super();
		this.destination = destination;
		this.typeOfTransport = typeOfTransport;
		this.price = price;
	}
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
