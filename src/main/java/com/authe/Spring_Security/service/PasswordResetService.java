package com.authe.Spring_Security.service;

import com.authe.Spring_Security.Repository.PasswordResetTokenRepository;
import com.authe.Spring_Security.Repository.UserRepository;
import com.authe.Spring_Security.Entity.PasswordResetToken;
import com.authe.Spring_Security.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordResetService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncode;

    public void createResetToken(Users user, String token){
        PasswordResetToken userToken = new PasswordResetToken();
        userToken.setToken(token);
        userToken.setUser(user);
        userToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        passwordResetTokenRepository.save(userToken);
    }
    public void sendPasswordResetToken(String email){
        Users user = userRepository.findByEmail(email).get();
        if (user == null)
            throw new IllegalArgumentException("No user with email " + email);
        String token = UUID.randomUUID().toString();
        createResetToken(user, token);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("http://localhost:4455/resetPassword?token="+ token);
        message.setFrom("testMail@mail.ru");
        mailSender.send(message);

    }
    public boolean isValidPasswordResetToken(String token){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).get();
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now()))
            return false;
        return true;
    }

    public void changePassword(Users user, String newPassword){
        user.setPassword(passwordEncode.encode(newPassword));
        userRepository.save(user);
    }

}
