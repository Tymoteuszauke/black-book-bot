package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.AuthorsRepository;
import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.model.Author;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import view.creation_model.AuthorCreationData;
import view.creation_model.BookData;
import view.creation_model.BookDiscountData;

import java.util.stream.Collectors;

@Service
public class BookDiscountParserService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BookstoresRepository bookstoresRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private BookDiscountsRepository bookDiscountsRepository;

    /**
     * Parser API (from BookDiscountData into BookDiscount entity)
     * Expected behavior is if BookDiscount with given book id and given bookstore id is found, it deletes it and
     * saves new BookDiscount
     */
    public BookDiscount parseBookDiscountData(BookDiscountData bookDiscountData) {
        BookDiscount BookDiscount = new BookDiscount();
        BookDiscount.setPrice(bookDiscountData.getPrice());
        BookDiscount.setBookDiscountDetails(bookDiscountData.getBookDiscountDetails());
        Book parsedBook = parseBookCreationData(bookDiscountData.getBookData());
        Book book = booksRepository.findByTitle(parsedBook.getTitle());
        if (book == null) {
            book = parsedBook;
        }
        BookDiscount.setBook(book);

        //TODO remove if clause since creation data without bookstore id will not be permitted
        if (bookDiscountData.getBookstoreId() != null) {
            BookDiscount.setBookstore(bookstoresRepository.findOne((long) bookDiscountData.getBookstoreId()));

            BookDiscount existingBookDiscount = bookDiscountsRepository.findByBookIdAndBookstoreId(book.getId(), bookDiscountData.getBookstoreId());
            if (existingBookDiscount != null) {
                bookDiscountsRepository.delete(existingBookDiscount);
            }
        }

        return BookDiscount;
    }

    private Book parseBookCreationData(BookData bookData) {
        Book book = new Book();
        book.setTitle(bookData.getTitle());
        book.setSubtitle(bookData.getSubtitle());
        book.setGenre(bookData.getGenre());
        book.setAuthors(bookData
                .getAuthors()
                .stream()
                .map(this::parseAuthorCreationData)
                .collect(Collectors.toList()));
        return book;
    }

    private Author parseAuthorCreationData(AuthorCreationData authorCreationData) {
        Author author = authorsRepository.findAuthorByNameAndSurname(authorCreationData.getName(), authorCreationData.getSurname());
        if (author == null) {
            author = new Author();
        }
        author.setName(authorCreationData.getName());
        author.setSurname(authorCreationData.getSurname());
        return author;
    }
}
