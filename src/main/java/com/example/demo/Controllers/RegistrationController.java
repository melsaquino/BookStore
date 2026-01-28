package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @GetMapping("/app/registration")
    public String ShowRegistrationPage(){
        return "registration";
    }
    @PostMapping("/app/registration")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("psw_repeat") String psw_repeat){
        System.out.println(email);
        System.out.println(isPasswordsMatch(password, psw_repeat));

        return "login";
    }
    public boolean isPasswordsMatch(String password, String psw_repeat){
        return password.equals(psw_repeat);
    }
}
