package org.unibl.etf.travelbuddy.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.unibl.etf.travelbuddy.mysql.AdDao;
import org.unibl.etf.travelbuddy.mysql.AdDto;


@ManagedBean
@SessionScoped
public class AdUserBean implements Serializable {
	private static final long serialVersionUID = 9034206682795507344L;

	private boolean from;
	private boolean to;
	
	
	public boolean isFrom() {
		return from;
	}

	public void setFrom(boolean from) {
		this.from = from;
	}

	public boolean isTo() {
		return to;
	}

	public void setTo(boolean to) {
		this.to = to;
	}
	private AdDto ad;
	private Date today;
	private MapModel fromModel;
	private MapModel toModel;
	private String centerFromMap;
	private String centerToMap;
	
	
	public MapModel getFromModel() {
		return fromModel;
	}

	public void setFromModel(MapModel fromModel) {
		this.fromModel = fromModel;
	}

	public MapModel getToModel() {
		return toModel;
	}

	public void setToModel(MapModel toModel) {
		this.toModel = toModel;
	}

	public String getCenterFromMap() {
		return centerFromMap;
	}

	public void setCenterFromMap(String centerFromMap) {
		this.centerFromMap = centerFromMap;
	}

	public String getCenterToMap() {
		return centerToMap;
	}

	public void setCenterToMap(String centerToMap) {
		this.centerToMap = centerToMap;
	}

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
		fromModel=new DefaultMapModel();
		toModel=new DefaultMapModel();
		centerFromMap="41.850033, -87.6500523";
		centerToMap="41.850033, -87.6500523";
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String param = req.getParameter("adId");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if (param != null) {
			int updateId;
			try {
				updateId = Integer.parseInt(param);
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
						.handleNavigation(FacesContext.getCurrentInstance(), null, "/error.xhtml?faces-redirect=true");
				return;
			}
			List<AdDto> temp = AdDao.selectById(updateId);
			if (temp.size() != 1) {
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
						.handleNavigation(FacesContext.getCurrentInstance(), null, "/error.xhtml?faces-redirect=true");
			} else {
				ad = temp.get(0);
				session.setAttribute("updateAd", ad);
			}
		} else {
			ad = new AdDto();
		}
		today=new Date();
		UserBean bean = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userBean");
		ad.setUser(bean.getUser());
		ad.setUserId(bean.getUser().getId());
	}

	@PostConstruct
	public void init() {
		
	}
	
	

	public void saveAd(ActionEvent event) {
		/*
		 * HttpSession session = (HttpSession)
		 * FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		 * HttpServletRequest req = (HttpServletRequest)
		 * FacesContext.getCurrentInstance().getExternalContext() .getRequest();
		 * 
		 * AdDto updatedAd = (AdDto) session.getAttribute("updateAd");
		 * if(req.getRequestURL().toString().contains("ad_update") && ad != null) {
		 * ad.setId(updatedAd.getId()); AdDao.update(ad); FacesMessage message = new
		 * FacesMessage("Update successfull", "Ad successfully updated.");
		 * message.setSeverity(FacesMessage.SEVERITY_INFO);
		 * FacesContext.getCurrentInstance().addMessage(null, message); } else {
		 */
		ad.setStatus(1);
		AdDao.insert(ad);
		FacesMessage message = new FacesMessage("Successfull", "Ad successfully posted");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		PrimeFaces.current().ajax().addCallbackParam("saved", true);
		System.out.println(ad);
		FacesContext.getCurrentInstance().addMessage(null, message);
        //}
	}
	public void clear() {
		ad=new AdDto();
		System.out.println("Clear form");
	}
	public void closeAd(ActionEvent event) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		AdDto ad = (AdDto) session.getAttribute("updateAd");
		AdDao.updateStatus(ad.getId(), 2);
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(), null, "user/my_ads.xhtml?faces-redirect=true");
	}
	
	public void onPointSelectFrom(PointSelectEvent event) {
		ad.setLocationFromLatitude(event.getLatLng().getLat());
		ad.setLocationFromLongitude(event.getLatLng().getLng());
	}

	public void onGeocodeFrom(GeocodeEvent event) {
		ad.setLocationFromLatitude(event.getResults().get(0).getLatLng().getLat());
		ad.setLocationFromLongitude(event.getResults().get(0).getLatLng().getLng());

	}

	public void onPointSelectTo(PointSelectEvent event) {
		ad.setLocationToLatitude(event.getLatLng().getLat());
		ad.setLocationToLongitude(event.getLatLng().getLng());

	}

	public void onGeocodeTo(GeocodeEvent event) {
		ad.setLocationToLatitude(event.getResults().get(0).getLatLng().getLat());
		ad.setLocationToLongitude(event.getResults().get(0).getLatLng().getLng());
	}

}
