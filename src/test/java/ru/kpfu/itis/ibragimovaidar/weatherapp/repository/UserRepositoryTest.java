package ru.kpfu.itis.ibragimovaidar.weatherapp.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.ibragimovaidar.weatherapp.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    private final String username = "example@example.com";
    private final String verificationCode = "verificationCode";
    User user = User.builder()
            .username(username)
            .verificationCode(verificationCode)
            .password("password")
            .build();

    @Test
    public void findByUsername_WhenUserWithUsernameExists_ReturnUser() {
        testEntityManager.persistAndFlush(user);
        Optional<User> actual = userRepository.findByUsername(username);
        assertAll(
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(username, actual.get().getUsername())
        );
    }

    @Test
    public void findByUsername_WhenUserWithUsernameDoesNotExist_ReturnEmptyOptional() {
        User user = User.builder()
                .username("example2@example.com")
                .password("password")
                .build();
        testEntityManager.persistAndFlush(user);
        Optional<User> actual = userRepository.findByUsername(username);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void findByVerificationCode_WhenUserExist_ReturnUser() {
        testEntityManager.persistAndFlush(user);
        Optional<User> actual = userRepository.findByVerificationCode(verificationCode);
        assertAll(
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(verificationCode, actual.get().getVerificationCode())
        );
    }

    @Test
    public void findByVerificationCode_WhenUserDoesNotExist_ReturnEmptyOptional() {
        User user = User.builder()
                .username("example2@example.com")
                .password("password")
                .verificationCode("anotherCode")
                .build();
        testEntityManager.persistAndFlush(user);
        Optional<User> actual = userRepository.findByVerificationCode(verificationCode);
        assertTrue(actual.isEmpty());
    }
}
