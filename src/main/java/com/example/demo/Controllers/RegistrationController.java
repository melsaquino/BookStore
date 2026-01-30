package com.example.demo.Controllers;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.RegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/api/registration")
    public String ShowRegistrationPage(HttpSession session){
        if (session != null &&  session.getAttribute("loggedIn")!=null && ((boolean) session.getAttribute("loggedIn"))) {
            return "redirect:/api/catalogue";
        }
        return "registration";
    }
    //https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
    @PostMapping("/api/registration")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("psw_repeat") String psw_repeat){
        RegistrationService registrationService;

        if(isPasswordsMatch(password, psw_repeat)){
            registrationService = new RegistrationService(userRepository);
            registrationService.registerUser(email,password);
            return "login";
        }
        return "redirect:/api/registration";

    }
    private boolean isPasswordsMatch(String password, String psw_repeat){
        return password.equals(psw_repeat);
    }
}
