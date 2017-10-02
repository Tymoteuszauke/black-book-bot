package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.Bookstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookstoreController {

    private BookstoresRepository bookstoresRepository;

    @Autowired
    BookstoreController(BookstoresRepository bookstoresRepository) {
        this.bookstoresRepository = bookstoresRepository;
    }

    @GetMapping(value = "/api/bookstores")
    public List<Bookstore> getBookstores() {
        return bookstoresRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Bookstore::getName))
                .collect(Collectors.toList());
    }
}
