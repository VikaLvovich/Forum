package by.bsuir.forum.repository;

import by.bsuir.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);

    User getUserById(long Id);

    List<User> findAll();
}
