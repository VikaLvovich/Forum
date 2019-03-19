package by.bsuir.forum.repository;

import by.bsuir.forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    void deleteCommentById(Long id);

    Long countCommentsByUser_Id(Long id);

    Long countCommentsByTopic_Id(Long topic_id);

    List<Comment> findCommentByUser_IdOrderByCreatedDateDesc(Long id);

    List<Comment> findCommentByTopic_Id(Long topic_id);
}
