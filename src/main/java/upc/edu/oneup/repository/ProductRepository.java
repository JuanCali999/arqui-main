package upc.edu.oneup.repository;

import upc.edu.oneup.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByUserId(int userId); // Nuevo método
}
