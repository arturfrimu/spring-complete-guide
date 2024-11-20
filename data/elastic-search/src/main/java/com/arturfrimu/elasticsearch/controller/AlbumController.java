package com.arturfrimu.elasticsearch.controller;


import com.arturfrimu.elasticsearch.entity.Album;
import com.arturfrimu.elasticsearch.repository.AlbumRepository;
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
@RequestMapping("/albums/search")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlbumController {

    AlbumRepository repository;

    @GetMapping
    public String searchAlbums(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Album> albums = repository.searchByQuery(searchText, pageable);

        if (albums.isEmpty()) {
            albums = repository.findAll(pageable);
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("albums", albums.getContent());
        model.addAttribute("totalPages", albums.getTotalPages());
        model.addAttribute("currentPage", page);

        return "albums";
    }
}
