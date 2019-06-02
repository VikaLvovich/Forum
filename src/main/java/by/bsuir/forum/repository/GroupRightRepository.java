package by.bsuir.forum.repository;

import by.bsuir.forum.entity.GroupRight;
import by.bsuir.forum.entity.key.GroupRightKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRightRepository extends JpaRepository<GroupRight, GroupRightKey> {
}
