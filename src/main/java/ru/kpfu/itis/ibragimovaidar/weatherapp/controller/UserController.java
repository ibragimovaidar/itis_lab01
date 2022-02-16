package ru.kpfu.itis.ibragimovaidar.weatherapp.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto){
    return ResponseEntity.ok(userService.createUser(createUserDto));
  }

}
