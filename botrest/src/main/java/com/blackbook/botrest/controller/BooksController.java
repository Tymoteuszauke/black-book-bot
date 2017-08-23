package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.AuthorsRepository;
import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Author;
import com.blackbook.botrest.model.Book;
import com.blackbook.botrest.model.BookCreationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/api/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Book> getBooks(@RequestParam(defaultValue = "") String query,
                               @RequestParam(required = false) String priceFrom,
                               @RequestParam(required = false) String priceTo,
                               Pageable pageable) {
        log.info("TRANSACTION: GET /api/books");

        if (priceFrom != null && priceTo != null && !priceFrom.isEmpty() && !priceTo.isEmpty()) {
            Double fromPrice = Double.parseDouble(priceFrom);
            Double toPrice = Double.parseDouble(priceTo);
//            return booksRepository.findBooksWithTextualSearchAndBetweenPrices(query, fromPrice, toPrice, pageable);
        }
//        return booksRepository.findBooksWithTextualSearch(query, pageable);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Book getBook(@PathVariable long id) {
        log.info(String.format("TRANSACTION: GET /api/books/%d", id));
        return booksRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Book addBook(@RequestBody BookCreationData data) {
        log.info("TRANSACTION: POST /api/books");

        Book book = new Book();
        book.setTitle(data.getTitle());
        book.setSubtitle(data.getSubtitle());
        book.setGenre(data.getGenre());

        List<Author> authors = data.getAuthors().stream()
                .map(authorCreationData -> {
                    Author author = new Author();
                    author.setName(authorCreationData.getName());
                    author.setSurname(authorCreationData.getSurname());
                    return authorsRepository.save(author);
                }).collect(Collectors.toList());

        book.setAuthors(authors);
        log.info("Build book before save: " + book.toString());
        booksRepository.save(book);
        return book;
    }
}
