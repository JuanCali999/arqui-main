package upc.edu.oneup.service;

import upc.edu.oneup.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order saveOrder(Order order);
    List<Order> getOrdersByUserId(int userId); // Nuevo método
}
