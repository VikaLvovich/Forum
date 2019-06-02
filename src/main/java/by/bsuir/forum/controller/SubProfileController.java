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
public class SubProfileController {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public SubProfileController(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("subprofile/{id}")
    public String displayProfileById(@PathVariable Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        User subuser = userRepository.getUserById(id);
        Long numberOfTopics = topicRepository.countTopicsByUser_Id(id);
        Long numberOfComments = commentRepository.countCommentsByUser_Id(id);
        Integer numberOfCommentMarks = subuser.getCommentMarks().size();
        model.addAttribute("subuser", subuser);
        model.addAttribute("user", user);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("numberOfComments", numberOfComments);
        model.addAttribute("numberOfCommentMarks", numberOfCommentMarks);
        return "subprofile";
    }

    @PostMapping("subprofile/{id}")
    public View subscription(@PathVariable String id, @RequestParam String action, @RequestParam String user_id, HttpServletRequest request) {
        switch (action) {
            case "sub":
                User user = userRepository.getUserById(Long.valueOf(user_id));
                for (User u : user.getInterestingUsers()) {
                    if (u.getId() == Long.valueOf(id)) {
                        String contextPath = request.getContextPath();
                        return new RedirectView(contextPath + "/subprofile/" + id);
                    }
                }
                User subuser = userRepository.getUserById(Long.valueOf(id));
                user.getInterestingUsers().add(subuser);
                userRepository.save(user);
                break;
            case "del":
                User mainUser = userRepository.getUserById(Long.valueOf(user_id));
                User removedUser = null;
                for (User u : mainUser.getInterestingUsers()) {
                    if (u.getId() == Long.valueOf(id)) {
                        removedUser = u;
                    }
                }
                if (removedUser != null) {
                    mainUser.getInterestingUsers().remove(removedUser);
                    userRepository.save(mainUser);
                }

                break;
        }
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/subprofile/" + id);
    }

}