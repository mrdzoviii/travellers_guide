package org.unibl.etf.travelbuddy.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import org.unibl.etf.travelbuddy.model.Ticket;
import org.unibl.etf.travelbuddy.mysql.AdDao;
import org.unibl.etf.travelbuddy.mysql.AdDto;
import org.unibl.etf.travelbuddy.util.ServiceUtility;


@ManagedBean
@SessionScoped
public class AdViewBean {
	private List<AdDto> data;
	private List<AdDto> ads;
	private boolean need;
	private boolean offer;
	private String from;
	private String to;
	private Date timeFrom;
	private Date timeTo;
	private List<Ticket> tickets;
	
	
	




	public List<Ticket> getTickets() {
		return tickets;
	}




	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}




	public List<String> complete(String query) {
		return AdDao.getLocations(query);
	}
	
	
	

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}
	private AdDto selectedAd;
	
	public List<AdDto> getData() {
		return data;
	}

	public void setData(List<AdDto> data) {
		this.data = data;
	}

	public List<AdDto> getAds() {
		return ads;
	}

	public void setAds(List<AdDto> ads) {
		this.ads = ads;
	}

	public boolean isNeed() {
		return need;
	}

	public void setNeed(boolean need) {
		this.need = need;
	}

	public boolean isOffer() {
		return offer;
	}

	public void setOffer(boolean offer) {
		this.offer = offer;
	}

	public AdDto getSelectedAd() {
		return selectedAd;
	}

	public void setSelectedAd(AdDto selectedAd) {
		this.selectedAd = selectedAd;
	}
	
	public AdViewBean() {
		super();
	}

	@PostConstruct
	public void init() {
		ads = AdDao.selectAll();
		data=new ArrayList<>(ads);
		need = true;
		offer = true;
		from="";
		to="";
		timeFrom=null;
		timeTo=null;
		tickets=ServiceUtility.getTickets();
	}
	public void onLoad() {
		ads = AdDao.selectAll();
		data=AdDao.selectAll();
		need = true;
		offer = true;
		from="";
		to="";
		timeFrom=null;
		timeTo=null;
	}
	public void filter(){
		ads.clear();
		if (need == false && offer == false)
			return;
		ads.addAll(data);
		if (need && offer)
			return;
		if (need) {
			ads = ads.stream().filter(x -> x.getCategory()==1)
					.collect(Collectors.toList());
		}
		if (offer) {
			ads = ads.stream().filter(x -> x.getCategory()==0)
					.collect(Collectors.toList());
		}
	}
	
	public void filterAds() {
		ads.clear();
		ads.addAll(AdDao.filterAds(timeFrom, timeTo, from, to));
		if (need == false && offer == false)
			return;
		ads.addAll(data);
		if (need && offer)
			return;
		if (need) {
			ads = ads.stream().filter(x -> x.getCategory()==1)
					.collect(Collectors.toList());
		}
		if (offer) {
			ads = ads.stream().filter(x -> x.getCategory()==0)
					.collect(Collectors.toList());
		}
		System.out.println(from+" "+to+" "+timeFrom+" "+timeTo);
	}
}
