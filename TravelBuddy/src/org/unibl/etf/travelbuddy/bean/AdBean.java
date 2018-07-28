package org.unibl.etf.travelbuddy.bean;
import java.util.Timer;
import java.util.TimerTask;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.unibl.etf.travelbuddy.util.ServiceUtility;

@ManagedBean
@ApplicationScoped
public class AdBean {
	private String text;
	private String image;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public AdBean(String text, String image) {
		super();
		this.text = text;
		this.image = image;
	}

	public AdBean() {
		super();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				AdBean ad = ServiceUtility.getRandomAd();
				text = ad.getText();
				image = "data:image/png;base64," + ad.getImage();
			}
		}, 0,10*60 * 1000);
		if (text == null && image == null) {
			AdBean ad = ServiceUtility.getRandomAd();
			text = ad.getText();
			image = "data:image/png;base64," + ad.getImage();
		}
	}
}
