package by.bsuir.forum.controller;

import by.bsuir.forum.entity.Comment;
import by.bsuir.forum.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CommentsController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("comments/{id}")
    public String displayCommentsByUser(@PathVariable String id, Model model) {
        List<Comment> comments = commentRepository.findCommentByUser_IdOrderByCreatedDateDesc(Long.parseLong(id));
        model.addAttribute("comments", comments);
        return "comments";
    }

}
