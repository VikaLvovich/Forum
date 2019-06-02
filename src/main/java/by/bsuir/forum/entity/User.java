package by.bsuir.forum.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "l_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 32)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "info")
    private String info;

    @OneToMany(mappedBy = "user")
    private List<Topic> topics;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<CommentMark> commentMarks;

    @ManyToMany(mappedBy = "users")
    private List<UserGroup> userGroups;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private UserStatus status;


    @ManyToMany(mappedBy = "interestingUsers")
    private List<User> subscriber;

    @ManyToMany
    @JoinTable(
            name = "l_subscription",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> interestingUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<CommentMark> getCommentMarks() {
        return commentMarks;
    }

    public void setCommentMarks(List<CommentMark> commentMarks) {
        this.commentMarks = commentMarks;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<User> getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(List<User> subscriber) {
        this.subscriber = subscriber;
    }

    public List<User> getInterestingUsers() {
        return interestingUsers;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInterestingUsers(List<User> interestingUsers) {
        this.interestingUsers = interestingUsers;
    }

    public String displayParsedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }

    public boolean hasRight(String code) {
        for(UserGroup ug: userGroups) {
            for(GroupRight gr : ug.getGroupRights()) {
                Right right = gr.getRight();
                if (right.getCode().equals(code) && gr.isRight()) {
                    return true;
                }
            }
        }
        return false;
    }
}
