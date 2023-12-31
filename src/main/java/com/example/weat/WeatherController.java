package com.example.weat;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.mail.MessagingException;

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
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric"; // Add "&units=metric" to the URL to get the temperature in Celsius
            RestTemplate restTemplate = new RestTemplate();
            String jsonResult = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = new JSONObject(jsonResult);

            String cityName = jsonObject.getString("name");
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double humidity = main.getDouble("humidity");
            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            String weatherInfo = String.format("<td>%s</td><td>%.2f °C</td><td>%.2f %%</td><td>%.2f m/s</td>", cityName, temperature, humidity, windSpeed);
            weatherInfoList.add(weatherInfo);
        }

        // format weather information as a table
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h2>Weather Information</h2>");
        sb.append("<table>");
        sb.append("<tr><th style='font-size: 20px; padding: 10px;'>City</th><th style=' font-size: 20px; padding: 10px;'>Temperature</th><th style='font-size: 20px; padding: 10px;'>Humidity</th><th style='font-size: 20px; padding: 10px; '>Wind Speed</th></tr>");
        for (String weatherInfo : weatherInfoList) {
            sb.append("<tr style = 'font-size: 20px; padding: 20px;'>").append(weatherInfo).append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body></html>");

        // send the weather information as an email
        String to = Constants.EMAIL2; // replace with the recipient email address
        String subject = "Weather Information";
        String body = sb.toString();
        try {
            emailService.sendEmail(to, subject, body);
            return "Email sent successfully!";
        } catch (MessagingException e) {
            return "Failed to send email.";
        }
    }
}
