package upc.edu.oneup.repository;

import upc.edu.oneup.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StroreRepository extends JpaRepository<Store, Integer> {
    List<Store> findByUserId(int userId); // Nuevo método para obtener tiendas por userId
}
