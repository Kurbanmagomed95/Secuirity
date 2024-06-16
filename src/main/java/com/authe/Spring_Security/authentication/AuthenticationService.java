package com.authe.Spring_Security.authentication;


import com.authe.Spring_Security.Repository.UserRepository;
import com.authe.Spring_Security.config.Jwt.JwtService;
import com.authe.Spring_Security.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        );


        authenticationManager.authenticate(authToken);


        Users users = userRepository.findByEmail(authenticationRequest.getUsername()).get();

        String jwt = jwtService.generateToken(users, generateExtrsClaims(users));

        return new AuthenticationResponse(jwt);

    }

    private Map<String, Object> generateExtrsClaims(Users users) {

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("name" , users.getSecondName());
        extraClaims.put("role" , users.getRole().name());

        return extraClaims;
    }
}
