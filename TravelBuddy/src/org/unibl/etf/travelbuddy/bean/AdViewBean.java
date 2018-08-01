package org.unibl.etf.travelbuddy.bean;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.unibl.etf.travelbuddy.mysql.AdDao;
import org.unibl.etf.travelbuddy.mysql.AdDto;

@ManagedBean
@ViewScoped
public class AdViewBean {
	private List<AdDto> data;
	private List<AdDto> ads;
	private boolean need;
	private boolean offer;
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
		data=AdDao.selectAll();
		need = true;
		offer = true;
		selectedAd = new AdDto();
	}
	public void filterByNeedOffer(){
		ads.clear();
		ads.addAll(data);
		if (need == false && offer == false)
			return;
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
}
