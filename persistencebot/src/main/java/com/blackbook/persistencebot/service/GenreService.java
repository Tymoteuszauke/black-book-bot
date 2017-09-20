package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.GenreRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.Genre;
import com.blackbook.utils.model.creationmodel.BookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GenreService {

    private GenreRepository genreRepository;
    private BooksRepository booksRepository;
    private Map<Book, Genre> genres = new ConcurrentHashMap<>();

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Autowired
    public void setBooksRepository(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Async
    public void addGenreToDatabase(BookData bookData, Book book) {
        Genre genre = genreRepository.findByName(bookData.getGenre());
        if (genre == null) {
            genre = new Genre(bookData.getGenre());
            genreRepository.save(genre);
        }
        genres.put(book, genre);
    }

    @Async
    public void setGenres() {
        genres.forEach((book, genre) -> {
            book.getGenres().add(genre);
            booksRepository.save(book);
        });
        genres.clear();
    }
}
