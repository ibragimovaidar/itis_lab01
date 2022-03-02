package ru.kpfu.itis.ibragimovaidar.weatherapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherReport;

public interface WeatherReportRepository extends JpaRepository<WeatherReport, Long> {

  @Query("SELECT report FROM WeatherReport report WHERE report.weatherRequest.id IN "
      + "(SELECT request.id from WeatherRequest request where request.city = :city)")
  List<WeatherReport> findWeatherReportsByCity(String city);
}
