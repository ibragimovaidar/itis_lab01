package ru.kpfu.itis.ibragimovaidar.weatherapp.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

  @Value("${owm.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;

  public String getWeather(String city){
    String url = String.format(
        "http://api.openweathermap.org/data/2.5/weather?appid=%s&q=%s", apiKey, city);
    return restTemplate.getForObject(url, String.class);
  }
}
