package com.example.weat;

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
    public List<String> getWeatherForCities() {
        String apiKey = "75489b6e94cec5e9f80c09786521e786";
        List<String> weatherInfoList = new ArrayList<>();

        for (String city : CITIES) {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            weatherInfoList.add(result);
        }

        // send the weather information as an email
        String to = "20010970@ycce.in"; // replace with the recipient email address
        String subject = "Weather Information";
        String body = String.join("\n\n", weatherInfoList);
        emailService.sendEmail(to, subject, body);

        return weatherInfoList;
    }
}