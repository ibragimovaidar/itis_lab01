package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.CreateUserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.dto.UserDto;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;
import ru.kpfu.itis.ibragimovaidar.weatherapp.repository.UserRepository;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringJUnitConfig(UserServiceImpl.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    private MailService mailService;

    @Autowired
    private UserService userService;

    private final Long id = 1L;
    private final String username = "username@example.com";
    private final String password = "password";
    private final String verificationCode = "verificationCode";
    private final String url = "/";
    private User user;

    @BeforeEach
    void setUp(){
         user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                 .verificationCode(verificationCode)
                .build();
    }

    @Test
    void createUser() {
        CreateUserDto createUserDto = new CreateUserDto(100L, username, password);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto newUser = userService.createUser(createUserDto, url);
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(createUserDto.getUsername(), newUser.getUsername()),
                () -> verify(userRepository, times(1)).save(any(User.class))
        );
    }

    @Test
    void verify_UserWithVerificationCodeExists_ReturnTrue() {
        when(userRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.of(user));
        boolean actual = userService.verify(verificationCode);
        assertAll(
                () -> assertTrue(actual),
                () -> assertNull(user.getVerificationCode()),
                () -> assertTrue(user.isEnabled()),
                () -> verify(userRepository, times(1)).findByVerificationCode(verificationCode)
        );
    }

    @Test
    void verify_WhenUserWithVerificationCodeDoesNotExist_ReturnFalse() {
        when(userRepository.findByVerificationCode(any())).thenReturn(Optional.empty());
        boolean actual = userService.verify(verificationCode);
        assertAll(
                () -> assertFalse(actual),
                () -> assertEquals(verificationCode, user.getVerificationCode()),
                () -> assertFalse(user.isEnabled()),
                () -> verify(userRepository, times(1)).findByVerificationCode(verificationCode)
        );
    }
}
