package ru.kpfu.itis.ibragimovaidar.weatherapp.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherRequest;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.WeatherService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping
  public ResponseEntity<WeatherRequest> getWeather(@RequestParam Optional<String> city, @RequestParam String username){
    return ResponseEntity.of(weatherService.requestWeather(city.orElse("Kazan"), username));
  }

  @GetMapping("/history")
  public ResponseEntity<Page<WeatherRequest>> getHistory(
      @RequestParam Integer page,
      @RequestParam Integer size){
    Pageable pageable;
    if (page != null && size != null){
      pageable = PageRequest.of(page,size);
    } else {
      pageable = Pageable.unpaged();
    }
    return ResponseEntity.ok(weatherService.getHistory(pageable));
  }
}
