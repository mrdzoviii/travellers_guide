package org.unibl.etf.traveladvertiser.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.unibl.etf.traveladvertiser.mysql.AdDao;
import org.unibl.etf.traveladvertiser.mysql.AdDto;
import org.unibl.etf.traveladvertiser.rest.model.Advertisement;

public class ServiceUtility {
	private static Random rand=new Random();
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
	
	public static double calculatePrice(double costPerDay,int days) {
		return costPerDay * days;
	}
	public static double calculateCostPerDay() {
		return 10.00 + rand.nextDouble() * 90.00 ;
	}
	public static Date getCurrentDate() {
		return new Date();
	}
	
	public static Date getOffsetDate(int offset,Date date) {
		return new Date(date.getTime()+offset*24*60*60*1000);
	}
	
	public static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, img.getType());
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
	public static long differeceInDays(Date date1, Date date2) {
		return TimeUnit.DAYS.convert(Math.abs(date1.getTime() - date2.getTime()), TimeUnit.MILLISECONDS);
	}
	
	public static String parseDouble(double price) {
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(price);
	}
}
