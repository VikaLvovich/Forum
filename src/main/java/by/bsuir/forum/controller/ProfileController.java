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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

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
        Integer numberOfCommentMarks = user.getCommentMarks().size();
        model.addAttribute("user", user);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfComments", numberOfComments);
        model.addAttribute("numberOfCommentMarks", numberOfCommentMarks);
        return "profile";
    }

    @GetMapping("profile/{id}")
    public String displayProfileById(@PathVariable Long id, Model model) {
        User user = userRepository.getUserById(id);
        Long numberOfTopics = topicRepository.countTopicsByUser_Id(id);
        Long numberOfComments = commentRepository.countCommentsByUser_Id(id);
        Integer numberOfCommentMarks = user.getCommentMarks().size();
        model.addAttribute("user", user);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfComments", numberOfComments);
        model.addAttribute("numberOfCommentMarks", numberOfCommentMarks);
        return "profile";
    }

    @PostMapping("profile")
    public View changeProfile(@RequestParam String user_id, @RequestParam("info") String info, HttpServletRequest request) {
        User user = userRepository.getUserById(Long.valueOf(user_id));
        user.setInfo(info);
        userRepository.save(user);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/profile");
    }

}