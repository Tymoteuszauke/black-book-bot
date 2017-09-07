package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    private Book foundBook;

    @BeforeMethod
    public void before() {
        foundBook = new Book();
        foundBook.setTitle("Pani Patrycja");
        foundBook.setSubtitle("Java developer");
        foundBook.setGenre("Poradnik");
        foundBook.setAuthors("Jan Tymke");
        foundBook.setBookPageUrl("www.bookstore.com/book/pani-patrycja");
        foundBook.setCoverUrl("www.covers.com/patkkka");
    }

    @Test
    public void shouldParseDiscountData() throws Exception {
        // Given
        booksRepository = mock(BooksRepository.class);
        bookstoresRepository = mock(BookstoresRepository.class);
        discountsRepository = mock(BookDiscountsRepository.class);

        service = new BookDiscountParserService(booksRepository, bookstoresRepository, discountsRepository);

        // When
        BookDiscount bookDiscount = service.parseBookDiscountData(discountData);

        // Then
        assertEquals(25.99, bookDiscount.getPrice());
        assertEquals("-25%", bookDiscount.getBookDiscountDetails());
        assertEquals("Pan Tymek", bookDiscount.getBook().getTitle());
        assertEquals("-", bookDiscount.getBook().getSubtitle());
        assertEquals("Biografia", bookDiscount.getBook().getGenre());
        assertEquals("Tymke Wergiliusz", bookDiscount.getBook().getAuthors());
        assertEquals("www.bookstore.com/book/pan-tymek", bookDiscount.getBook().getBookPageUrl());
        assertEquals("www.covers.com/tymek", bookDiscount.getBook().getCoverUrl());
    }

    @Test
    public void shouldParseFoundInRepoBook() throws Exception {
        // Given
        booksRepository = mock(BooksRepository.class);
        bookstoresRepository = mock(BookstoresRepository.class);
        discountsRepository = mock(BookDiscountsRepository.class);

        when(booksRepository.findByTitle("Pan Tymek")).thenReturn(foundBook);

        service = new BookDiscountParserService(booksRepository, bookstoresRepository, discountsRepository);

        // When
        BookDiscount bookDiscount = service.parseBookDiscountData(discountData);

        // Then
        assertEquals(25.99, bookDiscount.getPrice());
        assertEquals("-25%", bookDiscount.getBookDiscountDetails());
        assertEquals("Pani Patrycja", bookDiscount.getBook().getTitle());
        assertEquals("Java developer", bookDiscount.getBook().getSubtitle());
        assertEquals("Poradnik", bookDiscount.getBook().getGenre());
        assertEquals("Jan Tymke", bookDiscount.getBook().getAuthors());
        assertEquals("www.bookstore.com/book/pani-patrycja", bookDiscount.getBook().getBookPageUrl());
        assertEquals("www.covers.com/patkkka", bookDiscount.getBook().getCoverUrl());
    }
}