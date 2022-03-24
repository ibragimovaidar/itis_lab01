package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherReportDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherRequestDto;

import java.util.List;
import java.util.Optional;

public interface WeatherService {

    Optional<WeatherRequestDto> createWeatherRequest(String city, String username);

    Page<WeatherRequestDto> getHistory(Pageable pageable);

    Page<WeatherRequestDto> getHistoryByUsername(Pageable pageable, String username);

    Page<WeatherRequestDto> getHistoryByCity(Pageable pageable, String city);

    List<WeatherReportDto> getWeatherReportsByCity(String city);
}
