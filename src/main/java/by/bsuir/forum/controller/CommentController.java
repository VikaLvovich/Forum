package by.bsuir.forum.controller;

import by.bsuir.forum.entity.Comment;
import by.bsuir.forum.repository.CommentRepository;
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
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("comment/{id}")
    public String displayEditComment(@PathVariable String id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentRepository.findCommentById(Long.valueOf(id));
        model.addAttribute("comment", comment);
        model.addAttribute("topic", comment.getTopic());
        return "comment";
    }

    @PostMapping("comment")
    public View addComment(@RequestParam String comment_id, @RequestParam("info") String info, HttpServletRequest request) {
        Comment comment = commentRepository.findCommentById(Long.valueOf(comment_id));
        comment.setInfo(info);
        commentRepository.save(comment);
        String contextPath = request.getContextPath();
        return new RedirectView(contextPath + "/topic/" + comment.getTopic().getId());
    }
}

