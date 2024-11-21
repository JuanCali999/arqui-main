package upc.edu.oneup.service;

import upc.edu.oneup.model.Order;
import upc.edu.oneup.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User createUser(User user);
    User updateUserByUsername(String username, User updatedUser);  // Actualizar por username
    void deleteUser(int id);
    User getUserByUsernameAndPassword(String username, String password);
    List<Order> getOrdersByUserId(int id);
    User getUserByUsername(String username);  // Añadir este método
    boolean isUsernameTaken(String username); // New method
}