package org.unibl.etf.traveladvertiser.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.unibl.etf.traveladvertiser.mysql.AdDto;
import org.unibl.etf.traveladvertiser.util.ServiceUtility;



@ManagedBean
@SessionScoped
public class Ad implements Serializable {
	private static final long serialVersionUID = -6057902019315345496L;
	private AdDto ad=new AdDto();
	private boolean paid=false;
	private boolean free=false;
	
	public boolean isFree() {
		return free;
	}
	public double getPrice() {
		return ServiceUtility.calculatePrice(costPerDay, days);
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	private UploadedFile uploadedFile;
	private String type;
	private byte[] imageBytes;
	private double costPerDay=ServiceUtility.calculateCostPerDay();
	private int days;
	public Ad() {
		ad.setDateFrom(ServiceUtility.getCurrentDate());
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public AdDto getAd() {
		return ad;
	}
	public void setAd(AdDto ad) {
		this.ad = ad;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	public double getCostPerDay() {
		return costPerDay;
	}
	public void setCostPerDay(double costPerDay) {
		this.costPerDay = costPerDay;
	}
	public void saveAd() {
		
	}
	public void handleFileUpload(FileUploadEvent event) {
		
	}
	public StreamedContent getImage() {
		if(ad.getImage() != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(ad.getImage()));
		}
		return null;
	}
	public void onTypeChanged() {
		paid="Paid".equals(type);
		free="Free".equals(type);
	}
}
