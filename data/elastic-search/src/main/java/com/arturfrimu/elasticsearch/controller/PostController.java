package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Post;
import com.arturfrimu.elasticsearch.repository.PostRepository;
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
@RequestMapping("/posts/search")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostRepository repository;

    @GetMapping
    public String searchPosts(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = repository.searchByQuery(searchText, pageable);

        if (posts.isEmpty()) {
            posts = repository.findAll(pageable);
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", page);

        return "posts";
    }
}
