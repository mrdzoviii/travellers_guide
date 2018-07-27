package org.unibl.etf.traveladvertiser.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.unibl.etf.traveladvertiser.mysql.AdDao;
import org.unibl.etf.traveladvertiser.mysql.AdDto;
import org.unibl.etf.traveladvertiser.rest.model.Advertisement;

public class ServiceUtility {
	public static List<Advertisement> getAdvertisements(){
		List<Advertisement> ads=new ArrayList<>();
		List<AdDto> adsFromDatabase=AdDao.getAds();
		for(AdDto ad:adsFromDatabase) {
			ads.add(new Advertisement(ad.getText(),encodeImageToBase64Binary(ad.getImage())));
		}
		return ads;
	}
	private static String encodeImageToBase64Binary(byte[] image){
		if(image == null)
			return "";
        return new String(Base64.getEncoder().encodeToString(image));
    }
}
