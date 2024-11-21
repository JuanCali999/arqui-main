package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.Product;
import upc.edu.oneup.model.User;
import upc.edu.oneup.service.ProductService;
import upc.edu.oneup.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "The Product API")
@RestController
@RequestMapping("/api/smart/v1/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // Endpoint: GET /api/oneup/v1/products/{username}
    // Obtiene todos los productos asociados a un usuario
    @Transactional(readOnly = true)
    @GetMapping("/{username}")
    public ResponseEntity<List<Product>> getProductsByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        List<Product> products = productService.getProductsByUserId(user.getId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Endpoint: POST /api/oneup/v1/products/{username}
    // Crea un nuevo producto asociado a un usuario
    @Transactional
    @PostMapping("/{username}")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        validateProduct(product);
        product.setUser(user); // Asocia el producto al usuario
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // Endpoint: DELETE /api/oneup/v1/products/{id}
    // Elimina un producto por ID
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product with id: " + id + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    // Validaciones para el producto
    private void validateProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new ValidationException("Product name is required");
        }
        if (product.getProductPrice() == null || product.getProductPrice() <= 0) {
            throw new ValidationException("Product price is required and must be greater than 0");
        }
        if (product.getProductImageUrl() == null || product.getProductImageUrl().trim().isEmpty()) {
            throw new ValidationException("Product image URL is required");
        }
    }
}
