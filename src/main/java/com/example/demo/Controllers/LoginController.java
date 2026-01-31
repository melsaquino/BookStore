package com.example.demo.Controllers;

import com.example.demo.Repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String ShowLoginPage(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("loginError")!=null && (boolean)session.getAttribute("loginError")){
            model.addAttribute("errorMessage","Invalid Email or Password!");
            request.getSession().removeAttribute("loginError");


        }
        return "login";
    }

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute("loginError");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }

        return "redirect:/login"; // Redirect to a login page or confirmation
    }
}
