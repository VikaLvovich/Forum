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
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ProfileController(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("profile")
    public String displayMyProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        Long numberOfTopics = topicRepository.countTopicsByUser_Id(user.getId());
        Long numberOfComments = commentRepository.countCommentsByUser_Id(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfComments", numberOfComments);
        return "profile";
    }

    @GetMapping("profile/{id}")
    public String displayProfileById(@PathVariable Long id, Model model) {
        User user = userRepository.getUserById(id);
        Long numberOfTopics = topicRepository.countTopicsByUser_Id(id);
        Long numberOfComments = commentRepository.countCommentsByUser_Id(id);
        model.addAttribute("user", user);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfComments", numberOfComments);
        return "profile";
    }

}