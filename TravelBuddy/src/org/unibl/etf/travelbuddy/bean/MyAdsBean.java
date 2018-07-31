package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
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
	private MapModel showModel;
	
	public MapModel getShowModel() {
		return showModel;
	}
	public void setShowModel(MapModel showModel) {
		this.showModel = showModel;
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
	
	public void filterAds(){
		ads.clear();
		if (need == false && offer == false)
			return;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for (AdDto ad : data) {
			String departureTime = df.format(ad.getDepartureTime());
			String createDate = df.format(ad.getCreateTime());
			if (ad.getStartingPoint().contains(query == null ? "" : query)
					|| ad.getDestination().contains(query == null ? "" : query) 
					|| departureTime.contains(query == null ? "" : query)
					|| createDate.contains(query == null ? "" : query)
					|| ad.getTitle().contains(query == null ? "" : query))
				ads.add(ad);
		}
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
	public void delete() {
		
	}
	@PostConstruct
	public void init() {
		UserBean bean = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
		
		ads = AdDao.selectByUserId(bean.getUser().getId());
		data = AdDao.selectByUserId(bean.getUser().getId());
		need = true;
		offer = true;
		selectedAd = new AdDto();
		showModel=new DefaultMapModel();
	}
}
