package com.example.demo.Services;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Exceptions.UserExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    public RegistrationService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void registerUser(String email,String password) throws UserExistsException {
        if(userRepository.findByEmail(email)==null){
            String hashedPassword = encoder.encode(password);
            User user=new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);

            this.userRepository.save(user);
        }
        else throw new UserExistsException("User already exists!");


    }
}
