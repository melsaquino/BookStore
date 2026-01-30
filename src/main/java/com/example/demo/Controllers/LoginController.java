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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/login")
    public String ShowLoginPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null ) {
           return "login";
        }
        else if (session.getAttribute("loggedIn")==null)
            return "login";
        else if (!(boolean)session.getAttribute("loggedIn"))
            return "login";

        return "redirect:/api/catalogue";
    }
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/api/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
       /* if (session != null) {
            session.invalidate(); // Invalidate the session
        }*/
        return "redirect:/api/login"; // Redirect to a login page or confirmation
    }
}
