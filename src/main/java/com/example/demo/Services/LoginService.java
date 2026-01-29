package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class LoginService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    public LoginService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public boolean processLogin(String email, String password){
        if(userRepository.findByEmail(email)==null)
            return false;
        else{
            User user =userRepository.findByEmail(email);
            System.out.println(user.getEmail());
            if (encoder.matches(password,user.getPassword()))
                return true;
            else
                return false;
        }
    }
}
