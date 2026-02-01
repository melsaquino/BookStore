package com.example.demo.Controllers;

import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.RegistrationService;
import com.example.demo.Exceptions.UserExistsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/registration")
    public String ShowRegistrationPage(HttpSession session){
        if (session != null &&  session.getAttribute("loggedIn")!=null && ((boolean) session.getAttribute("loggedIn"))) {
            return "redirect:/books";
        }
        return "registration";
    }
    //https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
    @PostMapping("/registration")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("psw_repeat") String psw_repeat, Model model){
        RegistrationService registrationService;

        if(isPasswordsMatch(password, psw_repeat)){
            registrationService = new RegistrationService(userRepository);
            try{
                registrationService.registerUser(email,password);
                return "login";

            } catch (UserExistsException e) {
                model.addAttribute("errorMessage",e.getMessage());
                return "/registration";

            }

        }
        model.addAttribute("errorMessage","Passwords don't match!");

        return "/registration";

    }
    private boolean isPasswordsMatch(String password, String psw_repeat){
        return password.equals(psw_repeat);
    }
}
