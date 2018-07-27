package org.unibl.etf.traveladvertiser.mysql;

import java.util.Arrays;
import java.util.Date;

public class AdDto {
	private int id;
	private String text;
	private byte[] image;
	private Date dateFrom;
	private Date dateTo;
	public AdDto() {
		super();
	}
	
	public AdDto(int id, String text, byte[] image, Date dateFrom, Date dateTo) {
		super();
		this.id = id;
		this.text = text;
		this.image = image;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	@Override
	public String toString() {
		return "AdDto [id=" + id + ", text=" + text + ", image=" + Arrays.toString(image) + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + "]";
	}
	
}
