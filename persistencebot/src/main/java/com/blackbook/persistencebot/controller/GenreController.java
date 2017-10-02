package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.GenreRepository;
import com.blackbook.utils.model.view.GenreView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/genres")
public class GenreController {

    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<GenreView> getGenres() {
        return genreRepository
                .findAll()
                .stream()
                .map(genre -> new GenreView(genre.getId(), genre.getName()))
                .sorted(Comparator.comparing(GenreView::getName))
                .collect(Collectors.toList());

    }
}
