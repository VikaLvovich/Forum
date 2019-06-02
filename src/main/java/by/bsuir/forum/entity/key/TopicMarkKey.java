package by.bsuir.forum.entity.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TopicMarkKey implements Serializable {

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "topic_id")
    public Long topicId;

    public TopicMarkKey() {
    }

    public TopicMarkKey(Long userId, Long topicId) {
        this.userId = userId;
        this.topicId = topicId;
    }
}

