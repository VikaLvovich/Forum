package by.bsuir.forum.entity.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupRightKey implements Serializable {

    @Column(name = "right_id")
    public Long rightId;

    @Column(name = "user_group_id")
    public Long userGroupId;

    public GroupRightKey() {
    }

    public GroupRightKey(Long rightId, Long userGroupId) {
        this.rightId = rightId;
        this.userGroupId = userGroupId;
    }
}
