package upc.edu.oneup.controller;

import upc.edu.oneup.model.Order;
import upc.edu.oneup.model.User;
import upc.edu.oneup.service.OrderService;
import upc.edu.oneup.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "The Orders API")
@RestController
@RequestMapping("/api/smart/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // Endpoint: POST /api/oneup/v1/orders/{username}
    // Guarda una nueva orden asociada a un usuario
    @Transactional
    @PostMapping("/{username}")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order.setUser(user); // Asocia la orden al usuario
        Order savedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // Endpoint: GET /api/oneup/v1/orders/{username}
    // Obtiene todas las órdenes asociadas a un usuario
    @Transactional(readOnly = true)
    @GetMapping("/{username}")
    public ResponseEntity<List<Order>> getOrdersByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
