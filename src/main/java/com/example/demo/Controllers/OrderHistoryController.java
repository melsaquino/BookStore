package com.example.demo.Controllers;

import com.example.demo.DTO.OrderHistoryEntryDTO;
import com.example.demo.Entities.Book;
import com.example.demo.Entities.Order;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.OrderHistoryService;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class OrderHistoryController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/order_history/{userId}")
    public  String showOrderHistory(@PathVariable("userId")int userId, Model model, HttpSession session){
        UserService userService = new UserService(userRepository);

        if(userService.findUser((String)session.getAttribute("userEmail"))==userId){
            model.addAttribute("userId",userId);
            String currentUserRole = userService.findUserRole((String)session.getAttribute("userEmail"));
            if(currentUserRole.equals("ADMIN"))
                model.addAttribute("admin",true);
            return "history";
        };
        return"redirect:/books";
    }
}
