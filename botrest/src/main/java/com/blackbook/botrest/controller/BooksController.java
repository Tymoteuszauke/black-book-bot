package com.blackbook.botrest.controller;

import com.blackbook.botrest.dao.BooksRepository;
import com.blackbook.botrest.model.Book;
import com.blackbook.botrest.model.BookCreationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 16.08.17
 */

@RestController
@RequestMapping(value = "/api/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-author")
    public List<Book> getBooksByAuthor(@RequestParam String author) {
        return booksRepository.findAllByAuthor(author);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-title")
    public List<Book> getBooksByTitle(@RequestParam String title) {
        return booksRepository.findAllByTitle(title);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/more-expensive")
    public List<Book> getBooksMoreExpensiveThan(@RequestParam double price) {
        return booksRepository.findByPriceGreaterThan(price);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cheaper")
    public List<Book> getBooksCheaperThan(@RequestParam double price) {
        return booksRepository.findByPriceLessThan(price);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/in-range")
    public List<Book> getBooksWithPriceInRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return booksRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Book addBook(@RequestBody BookCreationData data) {

        Book book = new Book();
        book.setAuthor(data.getAuthor());
        book.setTitle(data.getTitle());
        book.setPrice(data.getPrice());

        booksRepository.save(book);
        return book;
    }
}
