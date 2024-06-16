package com.authe.Spring_Security.config.Jwt;

import com.authe.Spring_Security.Repository.UserRepository;
import com.authe.Spring_Security.Entity.Users;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")){

            filterChain.doFilter(request, response);
            return;
        }


        String jwt = authHeader.split(" ")[1];

        String email = jwtService.extractEmail(jwt);

        Users users = userRepository.findByEmail(email).get();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, users.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);



    }
}
