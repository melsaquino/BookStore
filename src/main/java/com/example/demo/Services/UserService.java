package com.example.demo.Services;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.UserDoesNotExist;
import com.example.demo.Repositories.UserRepository;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public int findUser(String email){
        User user = userRepository.findByEmail(email);
        if (user!=null)
            return user.getId();
        else
            return -1;
    }

    public String findUserRole(String email){
        User user = userRepository.findByEmail(email);
        if (user!=null)
            return user.getRole();
        else
            throw new UserDoesNotExist("This user does not exist");
    }


}
