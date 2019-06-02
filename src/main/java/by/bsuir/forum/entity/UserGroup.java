package by.bsuir.forum.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "l_user_group")
public class UserGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 60)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "l_user_to_group",
            joinColumns = @JoinColumn(name = "user_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "userGroup")
    private List<GroupRight> groupRights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<GroupRight> getGroupRights() {
        return groupRights;
    }

    public void setGroupRights(List<GroupRight> groupRights) {
        this.groupRights = groupRights;
    }

    public String displayParsedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return this.createdDate.format(formatter);
    }
}

