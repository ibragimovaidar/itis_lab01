package ru.kpfu.itis.ibragimovaidar.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

  private String username;

  public static UserDto fromUser(User user){
    return new UserDto(user.getUsername());
  }
}
