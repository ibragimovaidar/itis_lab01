package ru.kpfu.itis.ibragimovaidar.weatherapp.weather;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping
  public String getWeather(@RequestParam Optional<String> city){
    return weatherService.getWeather(city.orElse("Kazan"));
  }
}
