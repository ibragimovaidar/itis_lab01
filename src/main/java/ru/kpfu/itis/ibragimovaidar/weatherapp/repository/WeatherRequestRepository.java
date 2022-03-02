package ru.kpfu.itis.ibragimovaidar.weatherapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherRequest;

public interface WeatherRequestRepository extends JpaRepository<WeatherRequest, Long> {

  Page<WeatherRequest> findAll(Pageable pageable);

  Page<WeatherRequest> findAllByUser_Username(Pageable pageable, String username);

  Page<WeatherRequest> findAllByCityContains(Pageable pageable, String city);

  Page<WeatherRequest> findAllByCity(Pageable pageable, String city);
}
