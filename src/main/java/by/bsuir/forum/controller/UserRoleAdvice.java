package by.bsuir.forum.controller;

import by.bsuir.forum.entity.User;
import by.bsuir.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {ProfileController.class, CommentsController.class, TopicController.class,
TopicsController.class})
public class UserRoleAdvice {

    // ... autowire needed services
    @Autowired
    UserRepository userRepository;

    @ModelAttribute("userActive")
    public void currentUser(Model model)
    {
        User user = userRepository.getUserByUsername("admin");
        model.addAttribute("currentUser", user);
    }

}