package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherReportDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherRequestDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherReport;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherRequest;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.WeatherReportRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.WeatherRequestRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.impl.WeatherServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringJUnitConfig(WeatherServiceImpl.class)
public class WeatherServiceTest {

    @MockBean
    private WeatherRequestRepository weatherRequestRepository;

    @MockBean
    private WeatherReportRepository weatherReportRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService;

    private final String city = "city";
    private final String username = "username";
    private final String report = "report";
    private final User user = User.builder()
            .username(username)
            .build();
    private final WeatherReport weatherReport = WeatherReport.builder()
            .report(report)
            .build();
    private final WeatherRequest weatherRequest = WeatherRequest.builder()
            .city(city)
            .weatherReport(weatherReport)
            .user(user)
            .build();

    @Test
    void createWeatherRequest() {
        WeatherRequest weatherRequest = WeatherRequest.builder()
                .user(user)
                .weatherReport(weatherReport)
                .build();
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
        given(restTemplate.getForObject(any(), any())).willReturn(report);
        given(weatherRequestRepository.save(any())).willReturn(weatherRequest);
        Optional<WeatherRequestDto> actual = weatherService.createWeatherRequest(city, username);
        assertAll(
                () -> assertTrue(actual.isPresent()),
                () -> verify(weatherRequestRepository, times(1)).save(any())
        );
    }

    @Test
    void getHistory() {
        Pageable pageable = Pageable.unpaged();
        given(weatherRequestRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of(weatherRequest)));
        Page<WeatherRequestDto> actual = weatherService.getHistory(pageable);
        assertAll(
                () -> assertTrue(actual.getSize() > 0),
                () -> assertEquals(city, actual.getContent().get(0).getCity())
        );
    }

    @Test
    void getHistoryByUsername() {
        Pageable pageable = Pageable.unpaged();
        given(weatherRequestRepository.findAllByUser_Username(pageable, username))
                .willReturn(new PageImpl<>(List.of(weatherRequest)));
        Page<WeatherRequestDto> actual = weatherService.getHistoryByUsername(pageable, username);
        assertAll(
                () -> assertTrue(actual.getSize() > 0),
                () -> assertEquals(username, actual.getContent().get(0).getUser().getUsername())
        );
    }

    @Test
    void getHistoryByCity() {
        Pageable pageable = Pageable.unpaged();
        given(weatherRequestRepository.findAllByCity(pageable, city))
                .willReturn(new PageImpl<>(List.of(weatherRequest)));
        Page<WeatherRequestDto> actual = weatherService.getHistoryByCity(pageable, city);
        assertAll(
                () -> assertTrue(actual.getSize() > 0),
                () -> assertEquals(city, actual.getContent().get(0).getCity())
        );
    }

    @Test
    void getWeatherReportsByCity() {
        given(weatherReportRepository.findWeatherReportsByCity(city))
                .willReturn(List.of(weatherReport));
        List<WeatherReportDto> actual = weatherService.getWeatherReportsByCity(city);
        assertAll(
                () -> assertFalse(actual.isEmpty())
        );
    }
}
