package org.unibl.etf.travelbuddy.model;

public class Weather {
	private int temperature;
	private int pressure;
	private int humidity;
	private int tempMin;
	private int tempMax;
	private double windSpeed;
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
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
	public int getTempMin() {
		return tempMin;
	}
	public void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}
	public int getTempMax() {
		return tempMax;
	}
	public void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public Weather(int temperature, int pressure, int humidity, int tempMin, int tempMax, double windSpeed) {
		super();
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.windSpeed = windSpeed;
	}
	public Weather() {
		super();
	}
	
	
	

	
	
}
