package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.ibragimovaidar.weatherapp.config.MailConfig;
import ru.kpfu.itis.ibragimovaidar.weatherapp.service.impl.MailServiceImpl;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringJUnitConfig({MailServiceImpl.class})
class MailServiceTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private MailConfig mailConfig;

    @Autowired
    private MailService mailService;

    private final String mail = "example@example.org";
    private final String name = "Ivan";
    private final String verificationCode = "verificationCode";
    private final String url = "/verify/";

    @BeforeEach
    public void setUp(){
        when(mailConfig.getFrom()).thenReturn("from");
        when(mailConfig.getSender()).thenReturn("sender");
        when(mailConfig.getSubject()).thenReturn("subject");
        when(mailConfig.getContent()).thenReturn("{name} {url}");
    }

    @Test
    void sendVerificationMail() {
    }
}