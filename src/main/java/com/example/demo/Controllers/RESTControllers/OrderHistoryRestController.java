package com.example.demo.Controllers.RESTControllers;

import com.example.demo.DTO.OrderHistoryEntryDTO;
import com.example.demo.DTO.ShoppingCartEntryDTO;
import com.example.demo.Repositories.BooksCatalogueRepository;
import com.example.demo.Repositories.OrdersRepository;
import com.example.demo.Services.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderHistoryRestController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private BooksCatalogueRepository booksCatalogueRepository;
    @GetMapping("/order_history/{userId}")
    /**
     * This method is used to retrieve all the books that are in the user's order history
     * @return A response entity that communicates the http status and the OrderHistory will be in the body
     * @param userId the current user's id to ensure the orders of the correct user is shown to the correct user
     *  */
    public ResponseEntity<List<OrderHistoryEntryDTO>> showOrderHistory(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size,@PathVariable("userId")int userId, Model model){

        OrderHistoryService orderHistoryService=new OrderHistoryService(ordersRepository,booksCatalogueRepository);
        List<OrderHistoryEntryDTO> orders=orderHistoryService.getBooksOrderedByCustomerId(userId,page,size);
        model.addAttribute("userId",userId);
        return ResponseEntity.ok(orders);
    }
}
