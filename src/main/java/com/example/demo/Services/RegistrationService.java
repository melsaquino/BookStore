package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    public RegistrationService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void registerUser(String email,String password) {
        if(userRepository.findByEmail(email)==null){
            String hashedPassword = encoder.encode(password);
            // Save the username and hashedPassword to the database
            User user=new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);

            this.userRepository.save(user);
        }
        else
            System.out.println("Exists");

    }
}
