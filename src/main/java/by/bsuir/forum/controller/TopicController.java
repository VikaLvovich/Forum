package by.bsuir.forum.controller;

import by.bsuir.forum.repository.CommentRepository;
import by.bsuir.forum.repository.TopicRepository;
import by.bsuir.forum.entity.Comment;
import by.bsuir.forum.entity.Topic;
import by.bsuir.forum.repository.UserRepository;
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

    @Autowired
    public TopicController(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("topic/{id}")
    public String displayTopic(@PathVariable String id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Long user_id = userRepository.getUserByUsername(username).getId();
        Topic topic = topicRepository.findTopicById(Long.valueOf(id));
        List<Comment> comments = commentRepository.findCommentByTopic_Id(Long.valueOf(id));
        model.addAttribute("topic", topic);
        model.addAttribute("comments", comments);
        model.addAttribute("user_id", user_id);
        return "topic";
    }

    @PostMapping("topic/{id}")
    public View updateComment(@RequestParam String id_topic, @RequestParam String action, @RequestParam String id_comment,
                              @RequestParam(required = false) String state, HttpServletRequest request) {
        switch (action) {
            case "delete":
                commentRepository.deleteCommentById(Long.valueOf(id_comment));
                break;
        }
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + id_topic);
    }

    @PostMapping("topic")
    public View addComment(@RequestParam("info") String info,
                           @RequestParam("id_topic") String id_topic, @RequestParam("id_user") String id_user,
                           HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setInfo(info);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setTopic(topicRepository.findTopicById(Long.valueOf(id_topic)));
        comment.setUser(userRepository.getUserById(Long.parseLong(id_user)));
        commentRepository.save(comment);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + id_topic);
    }
}