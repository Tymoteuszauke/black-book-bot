package com.blackbook.persistencebot.service;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BooksRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.model.BookDiscount;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import static org.testng.Assert.assertEquals;

public class BookDiscountParserServiceTest {

    @InjectMocks
    private BookDiscountParserService service;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private BookstoresRepository bookstoresRepository;

    @Mock
    private BookDiscountsRepository bookDiscountsRepository;


    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testParseBookDiscountData() throws Exception {
        // Given
        BookData data = BookData.builder()
                .title("Pan Tymek")
                .subtitle("-")
                .genre("Biografia")
                .authors("Tymke Wergiliusz")
                .bookPageUrl("www.bookstore.com/book/pan-tymek")
                .coverUrl("www.covers.com/tymek")
                .build();
        BookDiscountData discountData = BookDiscountData.builder()
                .price(25.99)
                .bookstoreId(2)
                .bookDiscountDetails("-25%")
                .bookData(data)
                .build();

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
}