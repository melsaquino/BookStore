package com.example.demo.Services;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class LoginService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user==null)
            throw new UsernameNotFoundException("No user found!");
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(), new ArrayList<>());
        }
    }
