package upc.edu.oneup.repository;

import upc.edu.oneup.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(int userId); // Nuevo método para obtener órdenes por userId
}
