package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto;

public interface UserService {

    UserDto createUser(CreateUserDto createUserDto, String url);

    boolean verify(String verificationCode);
}
