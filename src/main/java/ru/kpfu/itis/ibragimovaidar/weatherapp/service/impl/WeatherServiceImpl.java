package ru.kpfu.itis.ibragimovaidar.weatherapp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherReportDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherRequestDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.exception.UserNotFoundException;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherReport;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherRequest;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.WeatherReportRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.WeatherRequestRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.WeatherService;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private final WeatherRequestRepository weatherRequestRepository;
  private final WeatherReportRepository weatherReportRepository;
  private final UserRepository userRepository;

  @Value("${owm.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;

    @Override
    public Optional<WeatherRequestDto> createWeatherRequest(String city, String username) {
    User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    String url = String.format(
        "http://api.openweathermap.org/data/2.5/weather?appid=%s&q=%s", apiKey, city);
    String report = restTemplate.getForObject(url, String.class);
    WeatherRequest weatherRequest = WeatherRequest.builder()
        .user(user)
        .city(city)
        .build();
    WeatherReport weatherReport = WeatherReport.builder()
        .weatherRequest(weatherRequest)
        .report(report)
        .build();
    weatherRequest.setWeatherReport(weatherReport);
    return Optional.of(WeatherRequestDto.from(weatherRequestRepository.save(weatherRequest)));
  }

  @Override
  public Page<WeatherRequestDto> getHistory(Pageable pageable) {
    return weatherRequestRepository.findAll(pageable).map(WeatherRequestDto::from);
  }

  @Override
  public Page<WeatherRequestDto> getHistoryByUsername(Pageable pageable, String username) {
    return weatherRequestRepository.findAllByUser_Username(pageable, username)
        .map(WeatherRequestDto::from);
  }

  @Override
  public Page<WeatherRequestDto> getHistoryByCity(Pageable pageable, String city) {
    return weatherRequestRepository.findAllByCity(pageable, city).map(WeatherRequestDto::from);
  }

  @Override
  public List<WeatherReportDto> getWeatherReportsByCity(String city) {
    return weatherReportRepository.findWeatherReportsByCity(city).stream()
        .map(WeatherReportDto::from)
        .collect(Collectors.toList());
  }
}
