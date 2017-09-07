package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.persistencebot.service.BookDiscountParserService;
import org.springframework.data.domain.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.bookdiscount.BookDiscountView;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BookDiscountsControllerTest {

    private BookDiscountsController controller;
    private BookDiscountsRepository repo;
    private BookDiscountParserService service;

    private BookData bookData = BookData.builder()
            .title("Pan Tymek")
            .subtitle("-")
            .genre("Biografia")
            .authors("Tymke Wergiliusz")
            .bookPageUrl("www.bookstore.com/book/pan-tymek")
            .coverUrl("www.covers.com/tymek")
            .build();
    private BookDiscountData bookDiscountData = BookDiscountData.builder()
            .price(25.99)
            .bookstoreId(2)
            .bookDiscountDetails("-25%")
            .bookData(bookData)
            .build();
    private List<BookDiscountData> discountList = Arrays.asList(bookDiscountData);

    private BookDiscount discount;
    private BookDiscount savedDiscount;

    @BeforeMethod
    public void before() {
        service = mock(BookDiscountParserService.class);
        repo = mock(BookDiscountsRepository.class);
        controller = new BookDiscountsController(repo, service);
    }

    @Test
    public void shouldGetBookDiscounts() throws Exception {
        // When
        Page<BookDiscountView> bookDiscounts = controller.getBookDiscounts("", "1", "100", null);

        // Then
        assertNotNull(bookDiscounts);
    }

    @Test
    public void shouldPostBookDiscounts() {
        // Given
        initDataForSavingTest();
        when(service.parseBookDiscountData(bookDiscountData)).thenReturn(discount);
        when(repo.save(discount)).thenReturn(savedDiscount);

        // When
        List<BookDiscountView> views = controller.postBookDiscounts(discountList);

        // Then
        assertEquals("-25%", views.get(0).getDiscountDetails());
        assertEquals(25.99, views.get(0).getPrice());

        assertEquals(2, views.get(0).getBookstoreView().getId());
        assertEquals("Cheap Book", views.get(0).getBookstoreView().getName());
        assertEquals("A lot of free books!", views.get(0).getBookstoreView().getDetails());

        assertEquals("Pan Tymek", views.get(0).getBookView().getTitle());
        assertEquals("-", views.get(0).getBookView().getSubtitle());
        assertEquals("Tymke Wergiliusz", views.get(0).getBookView().getAuthors());
        assertEquals("www.bookstore.com/book/pan-tymek", views.get(0).getBookView().getBookPageUrl());
        assertEquals("www.covers.com/tymek", views.get(0).getBookView().getCoverUrl());
    }

    private void initDataForSavingTest() {
        discount = new BookDiscount();
        discount.setBookDiscountDetails(bookDiscountData.getBookDiscountDetails());
        discount.setPrice(bookDiscountData.getPrice());

        Book book = new Book();
        book.setTitle(bookData.getTitle());
        book.setSubtitle(bookData.getSubtitle());
        book.setGenre(bookData.getGenre());
        book.setAuthors(bookData.getAuthors());
        book.setBookPageUrl(bookData.getBookPageUrl());
        book.setCoverUrl(bookData.getCoverUrl());
        discount.setBook(book);

        Bookstore bookstore = new Bookstore();
        bookstore.setId(bookDiscountData.getBookstoreId());
        bookstore.setName("Cheap Book");
        bookstore.setDetails("A lot of free books!");
        discount.setBookstore(bookstore);

        savedDiscount = discount;
        savedDiscount.setId(20);
    }
}