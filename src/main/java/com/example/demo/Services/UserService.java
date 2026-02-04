package com.example.demo.Services;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.UserDoesNotExist;
import com.example.demo.Repositories.UserRepository;
/**
 * Class that primarily serves to query the repository about a user
 * */
public class UserService {
    private final UserRepository userRepository;
    /**
     * Constructor for the service class
     * @param userRepository the JPA repository that interacts with the database
     * */
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    /**
     * finds the user in the database
     * @param email the user's email usually coming from the session
     * @return the user's ID from the database
     * */
    public int findUser(String email){
        User user = userRepository.findByEmail(email);
        if (user!=null)
            return user.getId();
        else
            return -1;
    }
    /**
     * finds the user's role in the database
     * @param email the user's email usually coming from the session
     * @return the user's role from the database
     * */
    public String findUserRole(String email){
        User user = userRepository.findByEmail(email);
        if (user!=null)
            return user.getRole();
        else
            throw new UserDoesNotExist("This user does not exist");
    }


}
