package upc.edu.oneup.service;

import upc.edu.oneup.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product saveProduct(Product product);
    void deleteProduct(int id);
    List<Product> getProductsByUserId(int userId); // Nuevo método
}
