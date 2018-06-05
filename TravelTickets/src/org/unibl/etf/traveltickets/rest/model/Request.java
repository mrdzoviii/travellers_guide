package org.unibl.etf.traveltickets.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Request {
	private String name;
	private String surname;
	private String mail;
	private String date;
	private String destination;
	private String transportType;
	
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Request(String name, String surname, String mail, String date, String destination) {
		super();
		this.name = name;
		this.surname = surname;
		this.mail = mail;
		this.date = date;
		this.destination = destination;
	}
	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Request [name=" + name + ", surname=" + surname + ", mail=" + mail + ", date=" + date + ", destination="
				+ destination + "]";
	}
	
}
