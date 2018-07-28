package org.unibl.etf.travelbuddy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.unibl.etf.travelbuddy.bean.AdBean;

public class ServiceUtility {
	public static final Random rand=new Random();
	public static final ResourceBundle bundle=ResourceBundle.getBundle("org.unibl.etf.travelbuddy.config.TravelBuddyConfig");
	public static List<AdBean> getAds() {
		List<AdBean> ads = new ArrayList<>();
		try {
			URL url = new URL(bundle.getString("travelAdvertiser.rest"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setRequestProperty("Accept", "application/json");
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String input;
				StringBuffer buffer=new StringBuffer();
				while ((input = reader.readLine()) != null) {
					buffer.append(input);
				}
				JSONArray array = new JSONArray(buffer.toString());
				for(int i=0; i<array.length(); i++) {
					ads.add(new AdBean(array.getJSONObject(i).getString("text"), array.getJSONObject(i).getString("image")));
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ads;	
		}
	public static AdBean getRandomAd() {
		List<AdBean> ads=getAds();
		if(ads.size()>0) {
		return ads.get(rand.nextInt(ads.size()));
		}
		return null;
	}
	
}
	