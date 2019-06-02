package by.bsuir.forum.controller;

import by.bsuir.forum.entity.*;
import by.bsuir.forum.entity.key.CommentMarkKey;
import by.bsuir.forum.entity.key.TopicMarkKey;
import by.bsuir.forum.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TopicController {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;
    private final CommentMarkRepository commentMarkRepository;
    private final TopicMarkRepository topicMarkRepository;

    @Autowired
    public TopicController(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository, CommentMarkRepository commentMarkRepository, TopicMarkRepository topicMarkRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
        this.commentMarkRepository = commentMarkRepository;
        this.topicMarkRepository = topicMarkRepository;
    }

    @GetMapping("topic/{id}")
    public String displayTopic(@PathVariable String id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        Long user_id = user.getId();
        Topic topic = topicRepository.findTopicById(Long.valueOf(id));
        List<Comment> comments = commentRepository.findCommentByTopic_Id(Long.valueOf(id));
        model.addAttribute("topic", topic);
        model.addAttribute("comments", comments);
        model.addAttribute("user_id", user_id);
        return "topic";
    }

    @PostMapping("topic/{id}")
    public View updateTopic(@RequestParam(required = false) String topic_id, @RequestParam String action,
                              @RequestParam(required = false) String comment_id, @RequestParam Integer mark,
                              @RequestParam(required = false) String user_id,
                              HttpServletRequest request) {
        switch (action) {
            case "delete":
                commentRepository.deleteCommentById(Long.valueOf(comment_id));
                break;
            case "commentmark":
                CommentMark commentMark = new CommentMark();
                commentMark.setId(new CommentMarkKey(Long.parseLong(user_id), Long.parseLong(comment_id)));
                commentMark.setMark(mark);
                commentMark.setCreatedDate(LocalDateTime.now());
                commentMark.setUser(userRepository.getUserById(Long.parseLong(user_id)));
                commentMark.setComment(commentRepository.findCommentById(Long.parseLong(comment_id)));
                commentMarkRepository.save(commentMark);
                break;
            case "topicmark":
                TopicMark topicMark = new TopicMark();
                topicMark.setId(new TopicMarkKey(Long.parseLong(user_id), Long.parseLong(topic_id)));
                topicMark.setMark(mark);
                topicMark.setCreatedDate(LocalDateTime.now());
                topicMark.setUser(userRepository.getUserById(Long.parseLong(user_id)));
                topicMark.setTopic(topicRepository.findTopicById(Long.parseLong(topic_id)));
                topicMarkRepository.save(topicMark);
                break;
        }
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + topic_id);
    }

    @PostMapping("topic")
    public View addComment(@RequestParam("info") String info,
                           @RequestParam("topic_id") String topic_id, @RequestParam("user_id") String user_id,
                           HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setInfo(info);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setTopic(topicRepository.findTopicById(Long.valueOf(topic_id)));
        comment.setUser(userRepository.getUserById(Long.parseLong(user_id)));
        commentRepository.save(comment);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + topic_id);
    }
}