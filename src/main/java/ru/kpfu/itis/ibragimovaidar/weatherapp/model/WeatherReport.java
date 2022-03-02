package ru.kpfu.itis.ibragimovaidar.weatherapp.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WeatherReport {

  @Id
  @GeneratedValue
  private Long id;

  @Type(type = "text")
  private String report;

  @Builder.Default
  private LocalDateTime reportDateTime = LocalDateTime.now();

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToOne(mappedBy = "weatherReport")
  private WeatherRequest weatherRequest;
}
