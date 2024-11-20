package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Comment;
import com.arturfrimu.elasticsearch.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments/search")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentRepository repository;

    @GetMapping
    public String searchComments(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = repository.searchByQuery(searchText, pageable);

        if (comments.isEmpty()) {
            comments = repository.findAll(pageable);
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("comments", comments.getContent());
        model.addAttribute("totalPages", comments.getTotalPages());
        model.addAttribute("currentPage", page);

        return "comments";
    }
}
