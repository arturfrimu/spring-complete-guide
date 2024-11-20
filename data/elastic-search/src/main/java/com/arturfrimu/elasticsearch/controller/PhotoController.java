package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Photo;
import com.arturfrimu.elasticsearch.repository.PhotoRepository;
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
@RequestMapping("/photos/search")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhotoController {

    PhotoRepository repository;

    @GetMapping
    public String searchPhotos(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Photo> photos = repository.searchByQuery(searchText, pageable);

        if (photos.isEmpty()) {
            photos = repository.findAll(pageable);
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("photos", photos.getContent());
        model.addAttribute("totalPages", photos.getTotalPages());
        model.addAttribute("currentPage", page);

        return "photos";
    }
}
