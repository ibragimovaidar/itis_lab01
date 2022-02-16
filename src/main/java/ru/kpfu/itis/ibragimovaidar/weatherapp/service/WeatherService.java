package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherRequest;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.WeatherRequestRepository;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherRequestRepository weatherRequestRepository;
  private final UserRepository userRepository;

  @Value("${owm.api.key}")
  private String apiKey;

  private final RestTemplate restTemplate;

  public Optional<WeatherRequest> requestWeather(String city, String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()){
      String url = String.format(
          "http://api.openweathermap.org/data/2.5/weather?appid=%s&q=%s", apiKey, city);
      String weatherObject = restTemplate.getForObject(url, String.class);
      WeatherRequest weatherRequest = WeatherRequest.builder()
          .weatherObject(weatherObject)
          .user(user.get())
          .requestDateTime(LocalDateTime.now())
          .build();
      return Optional.of(weatherRequestRepository.save(weatherRequest));
    }
    return Optional.empty();
  }

  public Page<WeatherRequest> getHistory(Pageable pageable){
    return weatherRequestRepository.findAll(pageable);
  }
}
