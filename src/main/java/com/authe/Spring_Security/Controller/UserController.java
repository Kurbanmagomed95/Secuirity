package com.authe.Spring_Security.Controller;

import com.authe.Spring_Security.authentication.AuthenticationRequest;
import com.authe.Spring_Security.authentication.AuthenticationService;
import com.authe.Spring_Security.service.UserService;
import com.authe.Spring_Security.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public void createUser(@RequestBody UserDTO user) {
        if (user.getPassword1().equals(user.getPassword2())){
            userService.createUser(user);
        }

    }

    @PostMapping("/login")
    public void loginUser(@RequestBody AuthenticationRequest request){
        authenticationService.login(request);
    }
    /*@PostMapping("/register")
    public void createUser(@RequestParam("email") String email, @RequestParam("password") String password,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("secondName") String secondName){
        Users user = new Users();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        userService.createUser(user);
    }*/
}
