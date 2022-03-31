package ru.kpfu.itis.ibragimovaidar.weatherapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.WeatherRequestDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.WeatherService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringJUnitConfig(WeatherController.class)
public class WeatherControllerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private WeatherController weatherController;

    private final String username = "username";
    private final String city = "Kazan";
    private final UserDto userDto = UserDto.builder()
            .username(username)
            .build();
    private final WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
            .city(city)
            .user(userDto)
            .requestDateTime(LocalDateTime.of(2022, 3, 30, 12, 0))
            .build();

    @Test
    public void getWeather() {
        given(weatherService.createWeatherRequest(city, "username")).willReturn(Optional.of(weatherRequestDto));
        ResponseEntity<WeatherRequestDto> actual = weatherController.getWeather(Optional.of(city), "username");
        assertAll(
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode()),
                () -> assertEquals(city, actual.getBody().getCity())
        );
    }

    @Test
    public void getHistory() {
        Pageable pageable = Pageable.unpaged();
        given(weatherService.getHistory(pageable)).willReturn(new PageImpl<>(List.of(weatherRequestDto)));
        ResponseEntity<Page<WeatherRequestDto>> actual = weatherController.getHistory(pageable, Optional.empty());
        assertAll(
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode()),
                () -> assertTrue(actual.getBody().getSize() > 0),
                () -> assertEquals(weatherRequestDto, actual.getBody().getContent().get(0))
        );
    }

    @Test
    public void getHistoryByUserUsername() {
        Pageable pageable = Pageable.unpaged();
        given(weatherService.getHistoryByUsername(pageable, username)).willReturn(new PageImpl<>(List.of(weatherRequestDto)));
        ResponseEntity<Page<WeatherRequestDto>> actual = weatherController.getHistoryByUserUsername(pageable, username);
        assertAll(
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode()),
                () -> assertTrue(actual.getBody().getSize() > 0),
                () -> assertEquals(username, actual.getBody().getContent().get(0).getUser().getUsername())
        );
    }

    @Test
    public void getHistoryByCity() {
        Pageable pageable = Pageable.unpaged();
        given(weatherService.getHistoryByCity(pageable, city)).willReturn(new PageImpl<>(List.of(weatherRequestDto)));
        ResponseEntity<Page<WeatherRequestDto>> actual = weatherController.getHistoryByCity(pageable, city);
        assertAll(
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode()),
                () -> assertTrue(actual.getBody().getSize() > 0),
                () -> assertEquals(city, actual.getBody().getContent().get(0).getCity())
        );
    }

    @Test
    public void getReportsByCity() {
    }
}
