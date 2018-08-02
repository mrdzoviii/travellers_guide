package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
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
public class AdViewBean implements Serializable {
	private static final long serialVersionUID = -8152816248191247809L;
	private List<AdDto> data;
	private List<AdDto> ads;
	private String from;
	private String to;
	private Date timeFrom;
	private Date timeTo;
	private List<Ticket> ticketData;
	private List<Ticket> tickets;
	private Ticket selectedTicket;
	private String category;
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<Ticket> getTicketData() {
		return ticketData;
	}
	
	public void setTicketData(List<Ticket> ticketData) {
		this.ticketData = ticketData;
	}

	public Ticket getSelectedTicket() {
		return selectedTicket;
	}
	public void setSelectedTicket(Ticket selectedTicket) {
		this.selectedTicket = selectedTicket;
	}
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
		from="";
		to="";
		category="All";
		timeFrom=null;
		timeTo=null;
		ticketData=ServiceUtility.getTickets();
		tickets=new ArrayList<>(ticketData);
		selectedTicket=new Ticket();
	}
	public void onLoad() {
		data=AdDao.selectAll();
		ticketData=ServiceUtility.getTickets();
		from="";
		to="";
		category="All";
		timeFrom=null;
		timeTo=null;
	}
	public void filter(){
		ads.clear();
		ads.addAll(data);
		if ("All".equals(category))
			return;
		if ("Need".equals(category)) {
			ads = ads.stream().filter(x -> x.getCategory()==1)
					.collect(Collectors.toList());
		}
		if ("Offer".equals(category)) {
			ads = ads.stream().filter(x -> x.getCategory()==0)
					.collect(Collectors.toList());
		}
	}
	
	public void filterAds() {
		ads.clear();
		tickets.clear();
		for(AdDto ad:data) {
			if(ad.getStartingPoint().toLowerCase().contains(from!=null?from.toLowerCase():"") &&
				ad.getDestination().toLowerCase().contains(to!=null?to.toLowerCase():"")){
				ads.add(ad);
				if(timeFrom!=null) {
					ads = ads.stream().filter(x -> x.getDepartureTime().after(timeFrom))
							.collect(Collectors.toList());
				}
				if(timeTo!=null) {
					ads = ads.stream().filter(x -> x.getDepartureTime().before(timeTo))
							.collect(Collectors.toList());
				}
			}
		}
		for(Ticket ticket:ticketData) {
			if(to!=null && ticket.getDestination().toLowerCase().contains(to.toLowerCase())) {
				tickets.add(ticket);
			}
		}
		
		if ("All".equals(category)) {
			return;
		}
		if ("Need".equals(category)) {
			ads = ads.stream().filter(x -> x.getCategory()==1)
					.collect(Collectors.toList());
		}
		if ("Offer".equals(category)) {
			ads = ads.stream().filter(x -> x.getCategory()==0)
					.collect(Collectors.toList());
		}
		
	}

	public void reset() {
		category="All";
		from="";
		to="";
		timeFrom=null;
		timeTo=null;
		tickets=new ArrayList<>(ticketData);
		ads=new ArrayList<>(data);
	}
	public static void main(String[] args) {
	}
}
	
