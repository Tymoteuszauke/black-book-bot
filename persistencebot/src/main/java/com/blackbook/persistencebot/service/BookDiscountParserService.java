package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.utils.view.creationmodel.BookData;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Expected behavior is when BookDiscount with given book id and given bookstore id is found, it deletes it and
     * saves new BookDiscount
     */
    @Transactional
    public BookDiscount parseBookDiscountData(BookDiscountData bookDiscountData) {
        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setPrice(bookDiscountData.getPrice());
        bookDiscount.setBookDiscountDetails(bookDiscountData.getBookDiscountDetails());
        Book parsedBook = parseBookData(bookDiscountData.getBookData());
        Book book = booksRepository.findByTitleAndSubtitle(parsedBook.getTitle(), parsedBook.getSubtitle());
        if (book == null) {
            book = parsedBook;
//            book = booksRepository.save(parsedBook);
        }
        bookDiscount.setBook(book);

        Bookstore bookstore = bookstoresRepository.findOne((long)bookDiscountData.getBookstoreId());
        BookDiscount currentDiscount = bookDiscountsRepository.findByBookIdAndBookstoreId(book.getId(), bookDiscountData.getBookstoreId());
        if (currentDiscount != null) {
            bookDiscountsRepository.delete(currentDiscount.getId());
        }

        bookDiscount.setBookstore(bookstore);

        return bookDiscountsRepository.save(bookDiscount);
    }

    private Book parseBookData(BookData bookData) {
        Book book = new Book();
        book.setTitle(bookData.getTitle());
        book.setSubtitle(bookData.getSubtitle());
        book.setGenre(bookData.getGenre());
        book.setAuthors(bookData.getAuthors());
        book.setPublisher(bookData.getPublisher());
        book.setBookPageUrl(bookData.getBookPageUrl());
        book.setCoverUrl(bookData.getCoverUrl());
        return book;
    }
}
