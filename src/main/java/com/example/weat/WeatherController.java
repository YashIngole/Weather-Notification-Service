package com.example.weat;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class WeatherController {

    private static final List<String> CITIES = Arrays.asList("Nagpur", "Pune", "Mumbai", "Delhi", "Chennai", "Kolkata", "Bangalore");

    @Autowired
    private EmailService emailService;

   @GetMapping("/weather")
public String getWeatherForCities() {
    String apiKey = Constants.API_KEY;
    List<String> weatherInfoList = new ArrayList<>();

    for (String city : CITIES) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String jsonResult = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(jsonResult);

        String cityName = jsonObject.getString("name");
        JSONObject main = jsonObject.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double humidity = main.getDouble("humidity");
        JSONObject wind = jsonObject.getJSONObject("wind");
        double windSpeed = wind.getDouble("speed");

        String weatherInfo = String.format("%-10s %-10s %-10s %-10s ", cityName, temperature, humidity, windSpeed);
        weatherInfoList.add(weatherInfo);
    }

    // format weather information as a table
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%-10s %-10s %-10s %-10s \n", "City", "Temperature", "Humidity", "Wind Speed"));
    for (String weatherInfo : weatherInfoList) {
        sb.append(weatherInfo).append("\n");
    }

    // send the weather information as an email
    String to = Constants.EMAIL2; // replace with the recipient email address
    String subject = "Weather Information";
    String body = sb.toString();
    emailService.sendEmail(to, subject, body);

    return sb.toString();
}
}