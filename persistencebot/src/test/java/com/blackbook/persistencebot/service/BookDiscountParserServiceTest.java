package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.utils.view.creationmodel.BookData;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class BookDiscountParserServiceTest {

    private BookDiscountParserService service;
    private BooksRepository booksRepository;
    private BookstoresRepository bookstoresRepository;
    private BookDiscountsRepository discountsRepository;

    private BookData data = BookData.builder()
            .title("Pan Tymek")
            .subtitle("-")
            .genre("Biografia")
            .authors("Tymke Wergiliusz")
            .bookPageUrl("www.bookstore.com/book/pan-tymek")
            .coverUrl("www.covers.com/tymek")
            .build();
    private BookDiscountData discountData = BookDiscountData.builder()
            .price(25.99)
            .bookstoreId(2)
            .bookDiscountDetails("-25%")
            .bookData(data)
            .build();

    @Test
    public void shouldParseDiscountData() throws Exception {
        // Given
        BookDiscount bookDiscount = prepareBookDiscount();

        booksRepository = mock(BooksRepository.class);
        bookstoresRepository = mock(BookstoresRepository.class);
        discountsRepository = mock(BookDiscountsRepository.class);

        when(discountsRepository.save(bookDiscount)).thenReturn(bookDiscount);
        service = new BookDiscountParserService(booksRepository, bookstoresRepository, discountsRepository);

        // When
        BookDiscount getDiscount = service.parseBookDiscountData(discountData);

        // Then
        assertEquals(25.99, getDiscount.getPrice());
        assertEquals("-25%", getDiscount.getBookDiscountDetails());
        assertEquals("Pan Tymek", getDiscount.getBook().getTitle());
        assertEquals("-", getDiscount.getBook().getSubtitle());
        assertEquals("Biografia", getDiscount.getBook().getGenre());
        assertEquals("Tymke Wergiliusz", getDiscount.getBook().getAuthors());
        assertEquals("www.bookstore.com/book/pan-tymek", getDiscount.getBook().getBookPageUrl());
        assertEquals("www.covers.com/tymek", getDiscount.getBook().getCoverUrl());
    }

    @Test
    public void shouldParseFoundInRepoBook() throws Exception {
        // Given
        Book foundBook = new Book();
        foundBook.setTitle("Pani Patrycja");
        foundBook.setSubtitle("Java developer");
        foundBook.setGenre("Poradnik");
        foundBook.setAuthors("Jan Tymke");
        foundBook.setBookPageUrl("www.bookstore.com/book/pani-patrycja");
        foundBook.setCoverUrl("www.covers.com/patkkka");

        BookDiscount bookDiscount = prepareBookDiscount(foundBook);

        booksRepository = mock(BooksRepository.class);
        bookstoresRepository = mock(BookstoresRepository.class);
        discountsRepository = mock(BookDiscountsRepository.class);

        when(booksRepository.findByTitleAndSubtitle("Pan Tymek", "-")).thenReturn(foundBook);
        when(discountsRepository.save(bookDiscount)).thenReturn(bookDiscount);

        service = new BookDiscountParserService(booksRepository, bookstoresRepository, discountsRepository);

        // When
        BookDiscount getDiscount = service.parseBookDiscountData(discountData);

        // Then
        assertEquals(25.99, getDiscount.getPrice());
        assertEquals("-25%", getDiscount.getBookDiscountDetails());
        assertEquals("Pani Patrycja", getDiscount.getBook().getTitle());
        assertEquals("Java developer", getDiscount.getBook().getSubtitle());
        assertEquals("Poradnik", getDiscount.getBook().getGenre());
        assertEquals("Jan Tymke", getDiscount.getBook().getAuthors());
        assertEquals("www.bookstore.com/book/pani-patrycja", getDiscount.getBook().getBookPageUrl());
        assertEquals("www.covers.com/patkkka", getDiscount.getBook().getCoverUrl());
    }

    @Test
    public void shouldParseDiscountDataWhenNoOfferWasInRepository() throws Exception {
        // Given
        BookDiscount bookDiscount = prepareBookDiscount();

        booksRepository = mock(BooksRepository.class);
        bookstoresRepository = mock(BookstoresRepository.class);
        discountsRepository = mock(BookDiscountsRepository.class);

        when(discountsRepository.save(bookDiscount)).thenReturn(bookDiscount);
        when(discountsRepository.findByBookIdAndBookstoreId(1, 2)).thenReturn(null);
        doNothing().when(discountsRepository).delete(any(Long.class));

        service = new BookDiscountParserService(booksRepository, bookstoresRepository, discountsRepository);

        // When
        BookDiscount getDiscount = service.parseBookDiscountData(discountData);

        // Then
        assertEquals(25.99, getDiscount.getPrice());
        assertEquals("-25%", getDiscount.getBookDiscountDetails());
        assertEquals("Pan Tymek", getDiscount.getBook().getTitle());
        assertEquals("-", getDiscount.getBook().getSubtitle());
        assertEquals("Biografia", getDiscount.getBook().getGenre());
        assertEquals("Tymke Wergiliusz", getDiscount.getBook().getAuthors());
        assertEquals("www.bookstore.com/book/pan-tymek", getDiscount.getBook().getBookPageUrl());
        assertEquals("www.covers.com/tymek", getDiscount.getBook().getCoverUrl());
    }

    private BookDiscount prepareBookDiscount() {
        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setId(34);
        bookDiscount.setPrice(25.99);
        bookDiscount.setBookDiscountDetails("-25%");
        Book book = new Book();
        book.setId(1);
        book.setTitle("Pan Tymek");
        book.setSubtitle("-");
        book.setGenre("Biografia");
        book.setAuthors("Tymke Wergiliusz");
        book.setBookPageUrl("www.bookstore.com/book/pan-tymek");
        book.setCoverUrl("www.covers.com/tymek");
        bookDiscount.setBook(book);
        Bookstore bookstore = new Bookstore();
        bookstore.setId(100);
        bookDiscount.setBookstore(bookstore);
        return bookDiscount;
    }

    private BookDiscount prepareBookDiscount(Book found) {
        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setId(34);
        bookDiscount.setPrice(25.99);
        bookDiscount.setBookDiscountDetails("-25%");
        Book book = new Book();
        book.setId(1);
        book.setTitle(found.getTitle());
        book.setSubtitle(found.getSubtitle());
        book.setGenre(found.getGenre());
        book.setAuthors(found.getAuthors());
        book.setBookPageUrl(found.getBookPageUrl());
        book.setCoverUrl(found.getCoverUrl());
        bookDiscount.setBook(book);
        Bookstore bookstore = new Bookstore();
        bookDiscount.setBookstore(bookstore);
        return bookDiscount;
    }
}