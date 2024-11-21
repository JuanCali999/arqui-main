package upc.edu.oneup.repository;

import upc.edu.oneup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);  // Añadir este método
}
