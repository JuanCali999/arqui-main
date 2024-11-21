package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.Store;
import upc.edu.oneup.model.User;
import upc.edu.oneup.service.StoreService;
import upc.edu.oneup.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Stores", description = "The Store API")
@RestController
@RequestMapping("/api/smart/v1/stores")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @Autowired
    public StoreController(StoreService storeService, UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    // Endpoint: GET /api/oneup/v1/stores/{username}
    // Obtiene todas las tiendas asociadas a un usuario por su username
    @Transactional(readOnly = true)
    @GetMapping("/{username}")
    public ResponseEntity<List<Store>> getStoresByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        List<Store> stores = storeService.getStoresByUserId(user.getId());
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    // Endpoint: POST /api/oneup/v1/stores/{username}
    // Crea una nueva tienda asociada a un usuario
    @Transactional
    @PostMapping("/{username}")
    public ResponseEntity<Store> createStore(@RequestBody Store store, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        validateStore(store);
        store.setUser(user); // Asocia la tienda al usuario
        Store newStore = storeService.saveStore(store);
        return new ResponseEntity<>(newStore, HttpStatus.CREATED);
    }

    // Endpoint: PUT /api/oneup/v1/stores/{storeId}/{username}
    // Actualiza una tienda existente asociada a un usuario
    @Transactional
    @PutMapping("/{storeId}/{username}")
    public ResponseEntity<Store> updateStore(@PathVariable int storeId, @PathVariable String username, @RequestBody Store updatedStore) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }

        Store existingStore = storeService.getStoreById(storeId);
        if (existingStore == null || existingStore.getUser().getId() != user.getId()) {
            throw new ResourceNotFoundException("Store with ID " + storeId + " not found or not associated with user " + username);
        }

        validateStore(updatedStore);
        updatedStore.setId(storeId);
        updatedStore.setUser(user);
        Store savedStore = storeService.saveStore(updatedStore);
        return new ResponseEntity<>(savedStore, HttpStatus.OK);
    }

    // Endpoint: DELETE /api/oneup/v1/stores/{storeId}/{username}
    // Elimina una tienda asociada a un usuario
    @Transactional
    @DeleteMapping("/{storeId}/{username}")
    public ResponseEntity<String> deleteStore(@PathVariable int storeId, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }

        Store existingStore = storeService.getStoreById(storeId);
        if (existingStore == null || existingStore.getUser().getId() != user.getId()) {
            throw new ResourceNotFoundException("Store with ID " + storeId + " not found or not associated with user " + username);
        }

        storeService.deleteStore(storeId);
        return new ResponseEntity<>("Store with ID " + storeId + " was deleted", HttpStatus.NO_CONTENT);
    }

    // Método para validar la información de la tienda
    private void validateStore(Store store) {
        if (store.getName() == null || store.getName().trim().isEmpty()) {
            throw new ValidationException("Store name is required");
        }
        if (store.getDirection() == null || store.getDirection().trim().isEmpty()) {
            throw new ValidationException("Store direction is required");
        }
        if (store.getPhone() == null || store.getPhone().trim().isEmpty()) {
            throw new ValidationException("Store phone is required");
        }
        if (store.getSupervisor() == null || store.getSupervisor().trim().isEmpty()) {
            throw new ValidationException("Store supervisor is required");
        }
    }
}
