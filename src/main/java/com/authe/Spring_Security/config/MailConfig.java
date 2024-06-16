package com.authe.Spring_Security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        mailSender.setPort(587);
        mailSender.setUsername("testMail@mail.ru");
        mailSender.setPassword("passApplication");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        return mailSender;
    }
}
