package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.User;
import upc.edu.oneup.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Users", description = "The User API")
@RestController
@RequestMapping("/api/smart/v1/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint: POST /api/smart/v1/users/authenticate
    // Autenticación de usuario

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userService.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(user.getId()));
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Endpoint: GET /api/smart/v1/users
    // Obtiene todos los usuarios
 
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint: GET /api/smart/v1/users/username/{username}
    // Obtiene un usuario por su username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Endpoint: POST /api/smart/v1/users
    // Crea un nuevo usuario

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody User user) {
        validateUser(user);
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Endpoint: PUT /api/smart/v1/users/username/{username}
    // Actualiza un usuario existente por su username
    @PutMapping("/username/{username}")
    @Transactional
    public ResponseEntity<User> updateUserByUsername(@PathVariable String username, @RequestBody User updatedUser) {
        validateUser(updatedUser);
        User updated = userService.updateUserByUsername(username, updatedUser);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Endpoint: DELETE /api/smart/v1/users/{id}
    // Elimina un usuario por ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.deleteUser(id);
            return new ResponseEntity<>("User with ID " + id + " was deleted", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
    }

    // Método para validar datos del usuario
    private void validateUser(User user) {
        if (user.getUsername() != null && (user.getUsername().trim().isEmpty() || user.getUsername().length() > 30)) {
            throw new ValidationException("Username must not be empty and must not be more than 30 characters");
        }

        if (user.getPassword() != null && (user.getPassword().trim().isEmpty() || user.getPassword().length() > 30)) {
            throw new ValidationException("Password must not be empty and must not be more than 30 characters");
        }

        if (user.getEmail() != null && (user.getEmail().trim().isEmpty() || user.getEmail().length() > 50)) {
            throw new ValidationException("Email must not be empty and must not be more than 50 characters");
        }

        if (userService.isUsernameTaken(user.getUsername())) {
            throw new ValidationException("Username is already taken");
        }
    }
}
