package by.bsuir.forum.controller;

import by.bsuir.forum.entity.Topic;
import by.bsuir.forum.repository.CommentRepository;
import by.bsuir.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopicsController {

    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public TopicsController(TopicRepository topicRepository, CommentRepository commentRepository) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("topics")
    public String displayAllTopics(Model model) {
        List<Topic> topics = topicRepository.findAll(new Sort(Sort.Direction.DESC, "createdDate"));
        model.addAttribute("topics", topics);
        model.addAttribute("commentRepository", commentRepository);
        return "topics";
    }

    @GetMapping("topics/user/{id}")
    public String displayTopicsByUser(@PathVariable String id, Model model) {
        List<Topic> topics = topicRepository.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(id));
        model.addAttribute("topics", topics);
        model.addAttribute("commentRepository", commentRepository);
        return "topics";
    }
}