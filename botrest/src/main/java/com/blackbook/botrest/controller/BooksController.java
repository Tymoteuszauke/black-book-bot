package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Book;
import com.blackbook.botrest.model.BookCreationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
//        book.setAuthor(data.getAuthor());
//        book.setTitle(data.getTitle());
//        book.setPrice(data.getPrice());

        booksRepository.save(book);
        return book;
    }
}
