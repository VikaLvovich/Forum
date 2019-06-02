package by.bsuir.forum.controller;


import by.bsuir.forum.entity.Topic;
import by.bsuir.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class EditTopicController {
    private final TopicRepository topicRepository;

    @Autowired
    public EditTopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @GetMapping("edittopic/{id}")
    public String displayEditComment(@PathVariable String id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topic topic = topicRepository.findTopicById(Long.valueOf(id));
        model.addAttribute("topic", topic);
        return "edittopic";
    }

    @PostMapping("edittopic")
    public View editTopic(@RequestParam String topic_id, @RequestParam("info") String info, HttpServletRequest request) {
        Topic topic = topicRepository.findTopicById(Long.valueOf(topic_id));
        topic.setInfo(info);
        topicRepository.save(topic);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + topic.getId());
    }
}

