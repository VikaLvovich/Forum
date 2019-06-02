package by.bsuir.forum.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "l_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 1024)
    private String info;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "comment")
    private List<CommentMark> commentMarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<CommentMark> getCommentMarks() {
        return commentMarks;
    }

    public void setCommentMarks(List<CommentMark> commentMarks) {
        this.commentMarks = commentMarks;
    }

    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }

    public String displayBeginning() {
        return (this.info.length() < 32) ? this.info.concat("...") : this.info.substring(0, 30).concat("...");
    }

    public Integer getMarkByUserId(Long userId) {
        for (CommentMark commentMark : commentMarks) {
            if (commentMark.getUser().getId() == userId) {
                return commentMark.getMark();
            }
        }
        return 0;
    }

    public Integer getResultMark() {
        Integer resultMark = 0;
        for (CommentMark commentMark : commentMarks) {
            resultMark += commentMark.getMark();
        }
        return resultMark;
    }

}
