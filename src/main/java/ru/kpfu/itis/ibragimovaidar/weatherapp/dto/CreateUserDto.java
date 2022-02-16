package ru.kpfu.itis.ibragimovaidar.weatherapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {

  private String id;

  @NotNull
  @Email
  private String username;

  @NotNull
  private String password;
}
