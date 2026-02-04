package com.example.demo.Services;

import com.example.demo.Entities.User;
import com.example.demo.Exceptions.InvalidAccessException;
import com.example.demo.Exceptions.InvalidUserRole;
import com.example.demo.Exceptions.PasswordMismatchException;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Exceptions.UserExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Service class deals with the functionalities of registering a new user
 * */
@Service
public class RegistrationService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    public RegistrationService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    /**
     * Method used to add the user to the users table of the database
     * @param email the user's email that serves as their username
     * @param password the user's chosen password
     * @param psw_repeat the user's input for the re-submit password making sure the user knows the password they are making
     * */
    public void registerUser(String email,String password,String psw_repeat) throws UserExistsException, PasswordMismatchException {
        if (!isPasswordsMatch(password,psw_repeat))
            throw new PasswordMismatchException("Passwords don't match. Re-enter passwords");
        if(userRepository.findByEmail(email)==null){
            String hashedPassword = encoder.encode(password);
            User user=new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setRole("regular");

            this.userRepository.save(user);
        }
        else throw new UserExistsException("User already exists!");
    }

    /**
     * Method used to add the user to the users table of the database
     * @param email the user's email that serves as their username
     * @param password the user's password
     * @param psw_repeat the admin's input for the re-submit password making sure they know the password they are making
     * @param role the user role the admin assigned
     * */
    public void registerUser(int admin_id,String email,String password,String psw_repeat,String role) throws Exception {
        if (!isPasswordsMatch(password,psw_repeat))
            throw new PasswordMismatchException("Passwords don't match. Re-enter passwords");
        if (!userRepository.findById(admin_id).getRole().equals("ADMIN"))
            throw new InvalidAccessException("Invalid access");
        if((!role.equals("ADMIN") && !role.equals("Regular"))){
            throw new InvalidUserRole("Role you are about to assign is not valid!");
        }
        if(userRepository.findByEmail(email)==null){
            String hashedPassword = encoder.encode(password);
            User user=new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setRole(role);
            this.userRepository.save(user);
        }
        else throw new UserExistsException("User already exists!");

    }
    /**
     * Method checks if the two passwords entered by the user matches
     * */
    private boolean isPasswordsMatch(String password, String psw_repeat){
        return password.equals(psw_repeat);
    }

}
