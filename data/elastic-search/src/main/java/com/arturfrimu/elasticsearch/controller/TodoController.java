package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Todo;
import com.arturfrimu.elasticsearch.repository.TodoRepository;
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
@RequestMapping("/todos/search")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TodoController {

    TodoRepository repository;

    @GetMapping
    public String searchTodos(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "true") boolean completed,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Todo> todos = repository.searchByQuery(searchText, completed, pageable);

        if (todos.isEmpty()) {
            todos = repository.findAllByCompleted(completed, pageable);
        }

        model.addAttribute("completed", completed);
        model.addAttribute("searchText", searchText);
        model.addAttribute("todos", todos.getContent());
        model.addAttribute("totalPages", todos.getTotalPages());
        model.addAttribute("currentPage", page);

        return "todos";
    }
}
