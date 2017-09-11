package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.dao.LogEventRepository;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.persistencebot.service.BookDiscountParserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import view.bookdiscount.BookDiscountView;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BookDiscountsControllerTest {

    private BookDiscountsController controller;
    private BookDiscountsRepository repo;
    private LogEventRepository logRepo;
    private BookstoresRepository bookStoreRepo;
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
        logRepo = mock(LogEventRepository.class);
        bookStoreRepo = mock(BookstoresRepository.class);
        controller = new BookDiscountsController(repo, service, logRepo, bookStoreRepo);
    }

    @DataProvider
    private Object[][] searchParamsProvider() {
        return new Object[][]{
                {"query1", "1", "100", null},
                {"query1", "1", "", null},
                {"query1", "", "100", null},
                {"query2", "", "", null},
        };
    }

    @Test(dataProvider = "searchParamsProvider")
    public void shouldGetBookDiscounts(String query, String priceFrom, String priceTo, Pageable pageable) throws Exception {
        // Given
        Book book = new Book();
        book.setId(12L);
        book.setTitle("Pan Tymek");
        book.setSubtitle("-");
        book.setAuthors("Tymke Wergiliusz");
        book.setGenre("Biografia");
        book.setCoverUrl("www.covers.com/tymek");
        book.setBookPageUrl("www.bookstore.com/book/pan-tymek");

        BookDiscount discount1 = new BookDiscount();
        discount1.setId(1L);
        discount1.setPrice(15.75);
        discount1.setBookDiscountDetails("-50%");
        discount1.setBook(book);

        BookDiscount discount2 = new BookDiscount();
        discount2.setId(1L);
        discount2.setPrice(19.99);
        discount2.setBookDiscountDetails("-45%");
        discount2.setBook(book);

        List<BookDiscount> discountList = new ArrayList<>();
        discountList.add(discount1);
        discountList.add(discount2);

        Page page = new PageImpl(discountList);
        when(repo.findAllTextualSearchBetweenPrices(query, 1d, 100d, pageable)).thenReturn(page);
        when(repo.findAllTextualSearch(query, pageable)).thenReturn(page);

        // When
        Page<BookDiscountView> bookDiscounts = controller.getBookDiscounts(query, priceFrom, priceTo, pageable);

        // Then
        assertEquals(2, bookDiscounts.getContent().size());
        assertEquals(15.75, bookDiscounts.getContent().get(0).getPrice());
        assertEquals(19.99, bookDiscounts.getContent().get(1).getPrice());
    }

    @Test(dataProvider = "searchParamsProvider")
    public void shouldReturnEmptyPageForNoDiscountsGetFromRepo(String query, String priceFrom, String priceTo, Pageable pageable) {
        // Given
        when(repo.findAllTextualSearchBetweenPrices(query, 1d, 100d, pageable)).thenReturn(null);
        when(repo.findAllTextualSearch(query, pageable)).thenReturn(null);

        // When
        Page<BookDiscountView> bookDiscounts = controller.getBookDiscounts(query, priceFrom, priceTo, pageable);

        // Then
        assertEquals(Collections.EMPTY_LIST, bookDiscounts.getContent());
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