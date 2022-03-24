package ru.kpfu.itis.ibragimovaidar.weatherapp.service.impl;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.MailService;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder encoder;
  private final MailService mailService;


  @Override
  public UserDto createUser(CreateUserDto createUserDto, String url) {
    User newUser =
        User.builder()
            .username(createUserDto.getUsername())
            .password(encoder.encode(createUserDto.getPassword()))
            .verificationCode(RandomString.make(64))
            .build();
    mailService.sendVerificationMail(
        newUser.getUsername(), newUser.getUsername(), newUser.getVerificationCode(), url);
    return UserDto.fromUser(userRepository.save(newUser));
  }

  @Override
  public boolean verify(String verificationCode) {
    Optional<User> userOptional = userRepository.findByVerificationCode(verificationCode);
    if (userOptional.isPresent()){
      User user = userOptional.get();
      user.setVerificationCode(null);
      user.setEnabled(true);
      userRepository.save(user);
      return true;
    }
    return false;
  }
}
