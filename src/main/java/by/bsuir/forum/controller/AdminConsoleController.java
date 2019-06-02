package by.bsuir.forum.controller;

import by.bsuir.forum.entity.User;
import by.bsuir.forum.repository.CommentRepository;
import by.bsuir.forum.repository.TopicRepository;
import by.bsuir.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminConsoleController {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AdminConsoleController(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("adminconsole")
    public String displayMyProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        Long numberOfTopics = topicRepository.countTopicsByUser_Id(user.getId());
        Long numberOfComments = commentRepository.countCommentsByUser_Id(user.getId());
        Integer numberOfCommentMarks = user.getCommentMarks().size();
        model.addAttribute("user", user);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfComments", numberOfComments);
        model.addAttribute("numberOfCommentMarks", numberOfCommentMarks);
        return "adminconsole";
    }

}
