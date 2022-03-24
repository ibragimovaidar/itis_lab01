package ru.kpfu.itis.ibragimovaidar.weatherapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {

    private String from;
    private String sender;
    private String subject;
    private String content;
}
