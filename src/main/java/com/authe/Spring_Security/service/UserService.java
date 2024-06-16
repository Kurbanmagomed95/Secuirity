package com.authe.Spring_Security.service;

import com.authe.Spring_Security.Repository.UserRepository;
import com.authe.Spring_Security.DTO.UserDTO;
import com.authe.Spring_Security.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email).get();
        if (user == null)
            throw new UsernameNotFoundException("user not found");
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public void createUser(UserDTO user){
        Users user2 = new Users();
        user2.setPassword(user.getPassword1());
        user2.setEmail(user.getEmail());
        user2.setFirstName(user.getFirstName());
        user2.setSecondName(user.getLastName());
        user2.setPassword(passwordEncoder.encode(user.getPassword1()));

        userRepository.save(user2);
    }
}
