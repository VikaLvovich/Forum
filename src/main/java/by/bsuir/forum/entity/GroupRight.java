package by.bsuir.forum.entity;

import by.bsuir.forum.entity.key.GroupRightKey;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "l_right_to_user_group")
public class GroupRight {

    @EmbeddedId
    public GroupRightKey id;

    @MapsId("rightId")
    @ManyToOne
    private Right right;

    @MapsId("userGroupId")
    @ManyToOne
    private UserGroup userGroup;

    @Column(name = "is_right")
    private boolean isRight;

    public GroupRightKey getId() {
        return id;
    }

    public void setId(GroupRightKey id) {
        this.id = id;
    }

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
