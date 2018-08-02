package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.unibl.etf.travelbuddy.mysql.AdDao;
import org.unibl.etf.travelbuddy.mysql.AdDto;

@ManagedBean
@RequestScoped
public class MyAdsBean implements Serializable {
	private static final long serialVersionUID = 5509064235720654985L;
	private boolean need;
	private boolean offer;
	private String query;
	private List<AdDto> ads;
	private List<AdDto> data;
	private AdDto selectedAd;
	private String category;
	private Date timeFrom;
	private Date timeTo;
	private String from;
	private String to;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public boolean isNeed() {
		return need;
	}
	public void setNeed(boolean find) {
		this.need = find;
	}
	public boolean isOffer() {
		return offer;
	}
	public void setOffer(boolean offer) {
		this.offer = offer;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<AdDto> getAds() {
		return ads;
	}
	public void setAds(List<AdDto> ads) {
		this.ads = ads;
	}
	public List<AdDto> getData() {
		return data;
	}
	public void setData(List<AdDto> data) {
		this.data = data;
	}
	public AdDto getSelectedAd() {
		return selectedAd;
	}
	public void setSelectedAd(AdDto selectedAd) {
		this.selectedAd = selectedAd;
	}
	public MyAdsBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		UserBean bean = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		ads = AdDao.selectByUserId(bean.getUser().getId());
		data = AdDao.selectByUserId(bean.getUser().getId());
		category="All";
		timeFrom=null;
		timeTo=null;
		from="";
		to="";
		selectedAd = new AdDto();
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
	}
}
