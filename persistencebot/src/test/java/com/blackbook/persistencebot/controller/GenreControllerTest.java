package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.GenreRepository;
import com.blackbook.persistencebot.model.Genre;
import com.blackbook.utils.model.view.GenreView;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

public class GenreControllerTest {

    public List<Genre> getGenreTestData() {
        Genre islam = new Genre();
        islam.setId(1);
        islam.setName("Islam");

        Genre dzieci = new Genre();
        dzieci.setId(2);
        dzieci.setName("dzieci");

        Genre lgbt = new Genre();
        lgbt.setId(3);
        lgbt.setName("LGBT");

        Genre sensacyjne = new Genre();
        sensacyjne.setId(4);
        sensacyjne.setName("Sensacyjne");

        return Stream.of(islam, dzieci, lgbt, sensacyjne).sorted(Comparator.comparing(Genre::getName)).collect(Collectors.toList());
    }

    @Test
    public void getGenresExpectTrue() {
        GenreRepository genreRepository = mock(GenreRepository.class);
        GenreController genreController = new GenreController();
        genreController.setGenreRepository(genreRepository);

        List<Genre> expectedGenres = getGenreTestData();
        Mockito.when(genreRepository.findAll()).thenReturn(expectedGenres);

        List<GenreView> actualGenres = genreController.getGenres();

        assertEquals(expectedGenres.size(), actualGenres.size());
        assertEquals(expectedGenres.get(0).getName(), actualGenres.get(0).getName());
        assertEquals(expectedGenres.get(1).getName(), actualGenres.get(1).getName());
        assertEquals(expectedGenres.get(2).getName(), actualGenres.get(2).getName());

    }

}