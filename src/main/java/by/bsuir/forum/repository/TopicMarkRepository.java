package by.bsuir.forum.repository;

import by.bsuir.forum.entity.TopicMark;
import by.bsuir.forum.entity.key.TopicMarkKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicMarkRepository extends JpaRepository<TopicMark, TopicMarkKey> {

}
