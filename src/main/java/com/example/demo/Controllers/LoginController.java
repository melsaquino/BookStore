package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {
    @GetMapping("/app/login")
    public String ShowLoginPage(){
        return "login";
    }
    @PostMapping("/app/login")
    public String loginProcess(){
        return "redirect:/";
    }
}
