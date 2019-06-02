package by.bsuir.forum.repository;

import by.bsuir.forum.entity.CommentMark;
import by.bsuir.forum.entity.key.CommentMarkKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentMarkRepository extends JpaRepository<CommentMark, CommentMarkKey> {

}
