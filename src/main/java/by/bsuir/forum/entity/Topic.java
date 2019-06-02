package by.bsuir.forum.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "l_topic")
public class Topic {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false, length = 1024)
    private String info;

    @Column(length = 32)
    private String category;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "topic")
    private List<Comment> comments;

    @OneToMany(mappedBy = "topic")
    private List<TopicMark> topicMarks;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<TopicMark> getTopicMarks() {
        return topicMarks;
    }

    public void setTopicMarks(List<TopicMark> topicMarks) {
        this.topicMarks = topicMarks;
    }

    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }

    public Integer getMarkByUserId(Long userId) {
        for (TopicMark topicMark : topicMarks) {
            if (topicMark.getUser().getId() == userId) {
                return topicMark.getMark();
            }
        }
        return 0;
    }

    public Integer getResultMark() {
        Integer resultMark = 0;
        for (TopicMark topicMark : topicMarks) {
            resultMark += topicMark.getMark();
        }
        return resultMark;
    }
}
