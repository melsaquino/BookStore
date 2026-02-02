package com.example.demo.Controllers.RESTControllers;

import com.example.demo.DTO.OrderHistoryEntryDTO;
import com.example.demo.DTO.ShoppingCartEntryDTO;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Services.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderHistoryRestController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @GetMapping("/order_history/{userId}")
    public ResponseEntity<List<OrderHistoryEntryDTO>> showOrderHistory(@PathVariable("userId")int userId, Model model){

        OrderHistoryService orderHistoryService=new OrderHistoryService(ordersRepository,booksCatalogueRepository);
        List<OrderHistoryEntryDTO> orders=orderHistoryService.getBooksOrderedByCustomerId(userId);
        model.addAttribute("userId",userId);
        return ResponseEntity.ok(orders);
    }
}
