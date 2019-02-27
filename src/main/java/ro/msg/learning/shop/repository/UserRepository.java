package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
