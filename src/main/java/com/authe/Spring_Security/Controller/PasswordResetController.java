package com.authe.Spring_Security.Controller;

import com.authe.Spring_Security.Repository.PasswordResetTokenRepository;
import com.authe.Spring_Security.authentication.AuthenticationRequest;
import com.authe.Spring_Security.service.PasswordResetService;
import com.authe.Spring_Security.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/password")
public class PasswordResetController {
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    /*@PostMapping("/recovery")
    public void resetPassword(@RequestParam("email") String email){
        passwordResetService.sendPasswordResetToken(email);
    }*/
    @PostMapping("/recovery")
    public void resetPassword(@RequestBody AuthenticationRequest request){
        passwordResetService.sendPasswordResetToken(request.getUsername());
    }

    @GetMapping("/resetPassword")
    public String displayResetPassword(@RequestParam("token") String token){
        if (!passwordResetService.isValidPasswordResetToken(token))
            //отобразить страницу смены пароля
            return "Invalid or expired token";
        return "Token is valid";
    }

    // кнопка "сменить пароль"
    @PostMapping("/savePassword")
    public String changePassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword){
        if (!passwordResetService.isValidPasswordResetToken(token)){
            return "Invalid or expired token";
        }
        Users user = passwordResetTokenRepository.findByToken(token).get().getUser();
        passwordResetService.changePassword(user, newPassword);
        return "Password successfully reset";
    }



}
