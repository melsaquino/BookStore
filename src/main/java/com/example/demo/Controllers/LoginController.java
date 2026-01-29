package com.example.demo.Controllers;

import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.LoginService;
import com.example.demo.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/login")
    public String ShowLoginPage(){
        return "login";
    }
    @PostMapping("/api/login")
    public String loginProcess(@RequestParam("email") String email, @RequestParam("password")String password){
        //insert logic
        LoginService loginService;
        loginService = new LoginService(userRepository);

        if (loginService.processLogin(email,password))
            return "redirect:/app/catalogue";
        else
            return "redirect:/api/login";
    }
}
