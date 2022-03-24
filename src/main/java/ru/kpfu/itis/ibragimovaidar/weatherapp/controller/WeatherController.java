package ru.kpfu.itis.ibragimovaidar.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherReportDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherRequestDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.WeatherService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping
  public ResponseEntity<WeatherRequestDto> getWeather(
      @RequestParam(required = false) Optional<String> city,
      @RequestParam String username) {
    return ResponseEntity.of(weatherService.createWeatherRequest(city.orElse("Kazan"), username));
  }

  @GetMapping("/history")
  public ResponseEntity<Page<WeatherRequestDto>> getHistory(
      Pageable pageable,
      @RequestParam(required = false) Optional<String> usernameOptional) {
    return usernameOptional
        .map(username -> ResponseEntity.ok(weatherService.getHistoryByUsername(pageable, username)))
        .orElse(ResponseEntity.ok(weatherService.getHistory(pageable)));
  }

  @GetMapping("/history/byUsername")
  public ResponseEntity<Page<WeatherRequestDto>> getHistoryByUserUsername(
      Pageable pageable,
      @RequestParam String username) {
    return ResponseEntity.ok(weatherService.getHistoryByUsername(pageable, username));
  }

  @GetMapping("/history/byCity")
  public ResponseEntity<Page<WeatherRequestDto>> getHistoryByCity(Pageable pageable,
      @RequestParam String city) {
    return ResponseEntity.ok(weatherService.getHistoryByCity(pageable, city));
  }

  @GetMapping("/report/byCity")
  public ResponseEntity<List<WeatherReportDto>> getReportsByCity(@RequestParam String city) {
    return ResponseEntity.ok(weatherService.getWeatherReportsByCity(city));
  }
}
