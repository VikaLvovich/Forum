package by.bsuir.forum.controller;

import by.bsuir.forum.entity.Topic;
import by.bsuir.forum.entity.User;
import by.bsuir.forum.repository.SectionRepository;
import by.bsuir.forum.repository.TopicRepository;
import by.bsuir.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class NewTopicController {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public NewTopicController(UserRepository userRepository, TopicRepository topicRepository, SectionRepository sectionRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.sectionRepository = sectionRepository;
    }

    @GetMapping("newtopic")
    public String displayMyProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("sections", sectionRepository.findAll());
        return "newtopic";
    }

    @PostMapping("newtopic")
    public View addTopic(@RequestParam("section") String section, @RequestParam("category") String category,
                         @RequestParam("title") String title, @RequestParam("info") String info,
                         @RequestParam("user_id") String user_id, HttpServletRequest request) {
        Topic topic = new Topic();
        topic.setSection(sectionRepository.getSectionById(Long.getLong(section)));
        topic.setCategory(category);
        topic.setInfo(info);
        topic.setTitle(title);
        topic.setCreatedDate(LocalDateTime.now());
        topic.setUser(userRepository.getUserById(Long.parseLong(user_id)));
        topicRepository.save(topic);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/profile");
    }
}
