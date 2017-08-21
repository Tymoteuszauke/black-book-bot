package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Book;
import com.blackbook.botrest.model.BookCreationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/more-expensive")
    public List<Book> getBooksMoreExpensiveThan(@RequestParam double price) {
        return booksRepository.findByPriceGreaterThan(price);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cheaper")
    public List<Book> getBooksCheaperThan(@RequestParam double price) {
        return booksRepository.findByPriceLessThan(price);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Book> getBooks(@RequestParam(defaultValue = "") String query,
                               @RequestParam(required = false) String priceFrom,
                               @RequestParam(required = false) String priceTo,
                               Pageable pageable) {

        if (priceFrom != null && priceTo != null) {
            Double fromPrice = Double.parseDouble(priceFrom);
            Double toPrice = Double.parseDouble(priceTo);
            return booksRepository.findBooksWithTextualSearchAndBetweenPrices(query, fromPrice, toPrice, pageable);
        }
        return booksRepository.findBooksWithTextualSearch(query, pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Book getBook(@PathVariable long id) {
        return booksRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Book addBook(@RequestBody BookCreationData data) {

        Book book = new Book();
        book.setAuthor(data.getAuthor());
        book.setTitle(data.getTitle());
        book.setPrice(data.getPrice());

        booksRepository.save(book);
        return book;
    }
}
