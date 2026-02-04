package com.example.demo.Controllers;

import com.example.demo.Exceptions.PasswordMismatchException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    /**
     * Shows the registration
     * */
    @GetMapping("/registration")
    public String ShowRegistrationPage(HttpSession session){
        if (session != null &&  session.getAttribute("loggedIn")!=null && ((boolean) session.getAttribute("loggedIn"))) {
            return "redirect:/books";
        }
        return "registration";
    }

    /**
     * Controller that triggers the registration service that will make the user
     * @email email the user will be used and will be their username
     * @password password to be saved once hashed
     *
     * */
    @PostMapping("/registration")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password,
                             @RequestParam("psw_repeat") String psw_repeat, RedirectAttributes redirectAttributes, Model model){
        RegistrationService registrationService;

        registrationService = new RegistrationService(userRepository);
        try{
            registrationService.registerUser(email.toLowerCase(),password,psw_repeat);
            redirectAttributes.addFlashAttribute("successMessage", "User Created Successful");

            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "/registration";
        }

    }
}
