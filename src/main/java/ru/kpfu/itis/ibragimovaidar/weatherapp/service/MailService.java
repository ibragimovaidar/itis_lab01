package ru.kpfu.itis.ibragimovaidar.weatherapp.service;

public interface MailService {

    void sendVerificationMail(String mail, String name, String code, String url);
}
