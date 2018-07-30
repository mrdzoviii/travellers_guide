package org.unibl.etf.travelbuddy.model;

public class Weather {
	private String temperature;
	private int pressure;
	private int humidity;
	private String tempMin;
	private String tempMax;
	private double windSpeed;
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public String getTempMin() {
		return tempMin;
	}
	public void setTempMin(String tempMin) {
		this.tempMin = tempMin;
	}
	public String getTempMax() {
		return tempMax;
	}
	public void setTempMax(String tempMax) {
		this.tempMax = tempMax;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public Weather() {
		super();
	}
	public Weather(String temperature, int pressure, int humidity, String tempMin, String tempMax, double windSpeed) {
		super();
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.windSpeed = windSpeed;
	}
	@Override
	public String toString() {
		return "Weather [temperature=" + temperature + ", pressure=" + pressure + ", humidity=" + humidity
				+ ", tempMin=" + tempMin + ", tempMax=" + tempMax + ", windSpeed=" + windSpeed + "]";
	}
	
	

	
	
}
