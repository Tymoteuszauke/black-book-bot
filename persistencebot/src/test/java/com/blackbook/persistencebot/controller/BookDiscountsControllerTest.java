package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.service.BookDiscountParserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.bookdiscount.BookDiscountView;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BookDiscountsControllerTest {

    @InjectMocks
    private BookDiscountsController controller;

    @Mock
    private BookDiscountsRepository bookDiscountsRepository;

    @Mock
    private BookDiscountParserService bookDiscountParserService;

    private BookData data1 = BookData.builder()
            .title("Pan Tymek")
            .subtitle("-")
            .genre("Biografia")
            .authors("Tymke Wergiliusz")
            .bookPageUrl("www.bookstore.com/book/pan-tymek")
            .coverUrl("www.covers.com/tymek")
            .build();
    private BookDiscountData bookDiscountData1 = BookDiscountData.builder()
            .price(25.99)
            .bookstoreId(2)
            .bookDiscountDetails("-25%")
            .bookData(data1)
            .build();

    private BookData data2 = BookData.builder()
            .title("Pan Tymke")
            .subtitle("-")
            .genre("Biografia")
            .authors("Tymke Wergiliusz")
            .bookPageUrl("www.bookstore.com/book/pan-tymke")
            .coverUrl("www.covers.com/tymke")
            .build();
    private BookDiscountData bookDiscountData2 = BookDiscountData.builder()
            .bookstoreId(2)
            .price(31.49)
            .bookDiscountDetails("-33%")
            .bookData(data2)
            .build();

    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetBookDiscounts() throws Exception {
        // When
        Page<BookDiscountView> bookDiscounts = controller.getBookDiscounts("", "1", "100", null);

        // Then
        assertNotNull(bookDiscounts);
    }
}