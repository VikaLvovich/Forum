package by.bsuir.forum.entity;

import by.bsuir.forum.entity.key.CommentMarkKey;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "l_comment_mark")
public class CommentMark {

    @EmbeddedId
    public CommentMarkKey id;

    @MapsId("userId")
    @ManyToOne
    private User user;

    @MapsId("commentId")
    @ManyToOne
    private Comment comment;

    @Column(nullable = false)
    private Integer mark;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public CommentMarkKey getId() {
        return id;
    }

    public void setId(CommentMarkKey id) {
        this.id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }
}
