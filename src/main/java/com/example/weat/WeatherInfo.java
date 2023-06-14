package com.example.weat;

public class WeatherInfo {
    private String cityName;
    private double temperature;
    private double humidity;
    private double windSpeed;

    public WeatherInfo(String cityName, double temperature, double humidity, double windSpeed) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
//add