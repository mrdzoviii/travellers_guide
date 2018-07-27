package org.unibl.etf.traveladvertiser.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Advertisement {
	private String text;
	private String image;
	
	public Advertisement() {
		super();
	}
	public Advertisement(String text, String image) {
		super();
		this.text = text;
		this.image = image;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
}
