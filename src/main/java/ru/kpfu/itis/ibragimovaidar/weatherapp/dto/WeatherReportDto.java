package ru.kpfu.itis.ibragimovaidar.weatherapp.dto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.WeatherReport;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherReportDto {

  private Long id;
  private String report;
  private LocalDateTime reportDateTime;

  public static WeatherReportDto from(WeatherReport weatherReport){
    return WeatherReportDto.builder()
        .id(weatherReport.getId())
        .report(weatherReport.getReport())
        .reportDateTime(weatherReport.getReportDateTime())
        .build();
  }
}
