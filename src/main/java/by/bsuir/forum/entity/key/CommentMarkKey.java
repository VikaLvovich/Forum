package by.bsuir.forum.entity.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommentMarkKey implements Serializable {

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "comment_id")
    public Long commentId;

    public CommentMarkKey() {
    }

    public CommentMarkKey(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
