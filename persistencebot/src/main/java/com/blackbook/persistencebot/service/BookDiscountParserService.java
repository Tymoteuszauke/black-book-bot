package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import javax.transaction.Transactional;

@Service
@Slf4j
public class BookDiscountParserService {

    private BooksRepository booksRepository;
    private BookstoresRepository bookstoresRepository;
    private BookDiscountsRepository bookDiscountsRepository;

    @Autowired
    public BookDiscountParserService(BooksRepository booksRepository, BookstoresRepository bookstoresRepository, BookDiscountsRepository bookDiscountsRepository) {
        this.booksRepository = booksRepository;
        this.bookstoresRepository = bookstoresRepository;
        this.bookDiscountsRepository = bookDiscountsRepository;
    }

    /**
     * Parser API (from BookDiscountData into BookDiscount entity)
     * Expected behavior is if BookDiscount with given book id and given bookstore id is found, it deletes it and
     * saves new BookDiscount
     */
    @Transactional
    public BookDiscount parseBookDiscountData(BookDiscountData bookDiscountData) {
        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setPrice(bookDiscountData.getPrice());
        bookDiscount.setBookDiscountDetails(bookDiscountData.getBookDiscountDetails());
        Book parsedBook = parseBookData(bookDiscountData.getBookData());
        Book book = booksRepository.findByTitle(parsedBook.getTitle());
        if (book == null) {
            book = parsedBook;
        }
        bookDiscount.setBook(book);

        return bookDiscount;
    }

    private Book parseBookData(BookData bookData) {
        Book book = new Book();
        book.setTitle(bookData.getTitle());
        book.setSubtitle(bookData.getSubtitle());
        book.setGenre(bookData.getGenre());
        book.setAuthors(bookData.getAuthors());
        book.setBookPageUrl(bookData.getBookPageUrl());
        book.setCoverUrl(bookData.getCoverUrl());
        return book;
    }
}
