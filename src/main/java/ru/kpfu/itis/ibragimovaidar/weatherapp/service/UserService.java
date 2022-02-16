package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final MessageDigest messageDigest;

  public UserDto createUser(CreateUserDto createUserDto){
    User newUser = User.builder()
        .username(createUserDto.getUsername())
        .password(new String(
            messageDigest.digest(createUserDto.getPassword().getBytes(StandardCharsets.UTF_8))))
        .build();
    return UserDto.fromUser(userRepository.save(newUser));
  }
}
