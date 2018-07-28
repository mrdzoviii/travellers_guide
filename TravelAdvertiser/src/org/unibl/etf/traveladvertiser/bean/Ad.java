package org.unibl.etf.traveladvertiser.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.xml.rpc.ServiceException;

import org.primefaces.event.FileUploadEvent;
import org.unibl.etf.ipbanka.soap.IpBankaSoapService;
import org.unibl.etf.ipbanka.soap.IpBankaSoapServiceServiceLocator;
import org.unibl.etf.ipbanka.soap.model.Account;
import org.unibl.etf.traveladvertiser.mysql.AdDao;
import org.unibl.etf.traveladvertiser.mysql.AdDto;
import org.unibl.etf.traveladvertiser.util.ServiceUtility;

@ManagedBean
@SessionScoped
public class Ad implements Serializable {
	private static final long serialVersionUID = -6057902019315345496L;
	private AdDto ad = new AdDto();
	private boolean paid = false;
	private boolean free = true;
	private Account account = new Account();
	private String type = "Free";
	private byte[] imageBytes;
	private double costPerDay = ServiceUtility.calculateCostPerDay();
	private int days = 1;
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isFree() {
		return free;
	}

	public double getPrice() {
		return ServiceUtility.calculatePrice(costPerDay, days);

	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Ad() {
		ad.setDateFrom(ServiceUtility.getCurrentDate());
	}

	private void clear() {
		account = new Account();
		ad = new AdDto();
		paid = false;
		free = true;
		type = "Free";
		costPerDay=ServiceUtility.calculateCostPerDay();
		ad.setDateFrom(ServiceUtility.getCurrentDate());
		days=1;
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
		boolean operationMade = false;
		if (ad.getImage() == null && (ad.getText() == null || ad.getText().isEmpty())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ad text or text image must be entered", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		if ("Free".equals(type)) {
			ad.setDateTo(ServiceUtility.getOffsetDate(1, ad.getDateFrom()));
			operationMade = true;
		} else {
			ad.setDateTo(ServiceUtility.getOffsetDate(days, ad.getDateFrom()));
			if (ServiceUtility.differeceInDays(ad.getDateFrom(), ad.getDateTo()) < 1) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ad date from and date to not valid", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

			try {
				IpBankaSoapServiceServiceLocator locator = new IpBankaSoapServiceServiceLocator();
				IpBankaSoapService service = locator.getIpBankaSoapService();
				if (service.validateData(account.getMail(), account.getName(), account.getSurname(),
						account.getCardNumber(), account.getCardType(), account.getExpirationTime(), account.getCvc(),
						ServiceUtility.parseDouble(getPrice()))) {
					operationMade = service.payTotal(account.getMail(), account.getName(), account.getSurname(),
							account.getCardNumber(), account.getCardType(), account.getExpirationTime(),
							account.getCvc(), ServiceUtility.parseDouble(getPrice()));
				} else {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You enetered invalid data",
							"");
					FacesContext.getCurrentInstance().addMessage(null, message);
					return;
				}
				if (!operationMade) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"You don't have enough money on your bank account", "");
					FacesContext.getCurrentInstance().addMessage(null, message);
					return;
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}

		if (operationMade) {
			AdDao.insertAd(ad);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ad succesufully added", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			clear();
		}

	}

	public void handleFileUpload(FileUploadEvent event) {
		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(event.getFile().getInputstream());
			BufferedImage resized = ServiceUtility.resize(originalImage, 200, 266);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String[] name = event.getFile().getFileName().split("\\.");
			ImageIO.write(resized, name[name.length - 1], baos);
			baos.flush();
			imageBytes = baos.toByteArray();
			baos.close();
			ad.setImage(imageBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTypeChanged() {
		paid = "Paid".equals(type);
		free = "Free".equals(type);
	}
}
