package ru.kpfu.itis.ibragimovaidar.weatherapp.dto;

import static ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto.fromUser;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherReport;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherRequestDto {

  private Long id;
  private String city;
  private UserDto user;
  private WeatherReportDto weatherReport;
  private LocalDateTime requestDateTime;

  public static WeatherRequestDto from(WeatherRequest weatherRequest){
    return WeatherRequestDto.builder()
        .id(weatherRequest.getId())
        .city(weatherRequest.getCity())
        .user(fromUser(weatherRequest.getUser()))
        .weatherReport(WeatherReportDto.from(weatherRequest.getWeatherReport()))
        .requestDateTime(weatherRequest.getRequestDateTime())
        .build();
  }
}
