package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.unibl.etf.travelbuddy.mysql.AdDao;
import org.unibl.etf.travelbuddy.mysql.AdDto;

@ManagedBean
@SessionScoped
public class AdUserBean implements Serializable {
	private static final long serialVersionUID = 9034206682795507344L;

	@ManagedProperty("#{userBean}")
	UserBean userBean;

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	private AdDto ad;
	private Date today;
	
	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public AdDto getAd() {
		return ad;
	}

	public void setAd(AdDto ad) {
		this.ad = ad;
	}

	public AdUserBean() {
		super();
			ad = new AdDto();
			UserBean bean = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("userBean");
			ad.setUser(bean.getUser());
			ad.setUserId(bean.getUser().getId());
		today = new Date();
	}

	@PostConstruct
	public void init() {

	}

	public void saveAd(ActionEvent event) {
			ad.setStatus(1);
			if (AdDao.insert(ad)) {
				PrimeFaces.current().ajax().addCallbackParam("saved", true);

			} else {
				FacesMessage message = new FacesMessage("Error", "Ad not posted");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
	}
	public void editAd() {
		if (AdDao.update(ad)) {
			ad = new AdDto();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Update successfull");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "/user/my_ads.xhtml?faces-redirect=true");
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Advertisement not updated");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	public void clear() {
		ad = new AdDto();
		ad.setUserId(userBean.getUser().getId());
		ad.setUser(userBean.getUser());
		FacesMessage message = new FacesMessage("Successfull", "Ad successfully posted");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, message);
		System.out.println("Clear form");
	}
	public void newAdLoad() {
		ad = new AdDto();
		ad.setUserId(userBean.getUser().getId());
		ad.setUser(userBean.getUser());
	}

	public void closeAd(ActionEvent event) {
		AdDao.updateStatus(ad.getId(), 2);
		FacesMessage message = new FacesMessage("Successfull", "Ad closed");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "/user/my_ads.xhtml?faces-redirect=true");
	}

	public void onPointSelectFrom(PointSelectEvent event) {
		ad.setLocationFromLatitude(event.getLatLng().getLat());
		ad.setLocationFromLongitude(event.getLatLng().getLng());
		ad.setFrom(true);
	}

	public void onGeocodeFrom(GeocodeEvent event) {
		ad.setLocationFromLatitude(event.getResults().get(0).getLatLng().getLat());
		ad.setLocationFromLongitude(event.getResults().get(0).getLatLng().getLng());
		ad.setFrom(true);
	}

	public void onPointSelectTo(PointSelectEvent event) {
		ad.setLocationToLatitude(event.getLatLng().getLat());
		ad.setLocationToLongitude(event.getLatLng().getLng());
		ad.setTo(true);

	}

	public void onGeocodeTo(GeocodeEvent event) {
		ad.setLocationToLatitude(event.getResults().get(0).getLatLng().getLat());
		ad.setLocationToLongitude(event.getResults().get(0).getLatLng().getLng());
		ad.setTo(true);
	}

	public List<String> complete(String query) {
		return AdDao.getLocations(query);
	}

}
