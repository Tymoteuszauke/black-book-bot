package com.blackbook.utils.model.view;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class BookDiscountViewTest {

    private BookDiscountView discountView;
    private BookDiscountView otherDiscountView;
    private BookView bookView;
    private BookView otherBookView;
    private BookstoreView bookstoreView;
    private BookstoreView otherBookstoreView;

    @BeforeMethod
    public void before() {
        bookView = new BookView();
        bookView.setId(1L);
        bookView.setTitle("Pan Tymek");
        bookView.setSubtitle("Life");
        bookView.setAuthors("Tymke Wergiliusz");
        bookView.setPublisher("ACME");
        bookView.setBookPageUrl("www.tymek.pl");
        bookView.setCoverUrl("www.covers.com/tymek");

        otherBookView = new BookView();
        otherBookView.setId(1L);
        otherBookView.setTitle("Pan Tymek");
        otherBookView.setSubtitle("Life");
        otherBookView.setAuthors("Tymke Wergiliusz");
        otherBookView.setPublisher("ACME");
        otherBookView.setBookPageUrl("www.tymek.pl");
        otherBookView.setCoverUrl("www.covers.com/tymek");

        bookstoreView = new BookstoreView();
        bookstoreView.setId(1L);
        bookstoreView.setDetails("Great store");
        bookstoreView.setName("Store");

        otherBookstoreView = new BookstoreView();
        otherBookstoreView.setId(1L);
        otherBookstoreView.setDetails("Great store");
        otherBookstoreView.setName("Store");

        discountView = new BookDiscountView();
        discountView.setId(1L);
        discountView.setPrice(24.99);
        discountView.setDiscountDetails("-45%");
        discountView.setBookView(bookView);
        discountView.setBookstoreView(bookstoreView);

        otherDiscountView = new BookDiscountView();
        otherDiscountView.setId(1L);
        otherDiscountView.setPrice(24.99);
        otherDiscountView.setDiscountDetails("-45%");
        otherDiscountView.setBookView(bookView);
        otherDiscountView.setBookstoreView(bookstoreView);
    }

    @Test
    public void shouldReturnTrueForSameObjects() throws Exception {
        // Then
        assertTrue(discountView.equals(otherDiscountView));
    }

    @Test
    public void shouldReturnSameHashCodeForSameObjects() throws Exception {
        // Then
        assertEquals(discountView.hashCode(), otherDiscountView.hashCode());
    }

    @Test
    public void shouldReturnSameStringsForSameObjects() throws Exception {
        // Then
        assertEquals(discountView.toString(), otherDiscountView.toString());
    }

    @Test
    public void shouldReturnFalseForDiscountWithDifferentId() throws Exception {
        // When
        otherDiscountView.setId(5L);

        // Then
        assertFalse(discountView.equals(otherDiscountView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForDiscountWithDifferentId() throws Exception {
        // When
        otherDiscountView.setId(5L);

        // Then
        assertNotEquals(discountView.hashCode(), otherDiscountView.hashCode());
    }

    @Test
    public void shouldReturnFalseForDiscountWithDifferentPrice() throws Exception {
        // When
        otherDiscountView.setPrice(10.33);

        // Then
        assertFalse(discountView.equals(otherDiscountView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForDiscountWithDifferentPrice() throws Exception {
        // When
        otherDiscountView.setPrice(10.33);

        // Then
        assertNotEquals(discountView.hashCode(), otherDiscountView.hashCode());
    }

    @Test
    public void shouldReturnFalseForDiscountWithDifferentDiscountDetails() throws Exception {
        // When
        otherDiscountView.setDiscountDetails("-33%");

        // Then
        assertFalse(discountView.equals(otherDiscountView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForDiscountWithDifferentDiscountDetails() throws Exception {
        // When
        otherDiscountView.setDiscountDetails("-33%");

        // Then
        assertNotEquals(discountView.hashCode(), otherDiscountView.hashCode());
    }

    @Test
    public void shouldReturnFalseForDiscountWithDifferentBookView() throws Exception {
        // When
        otherBookView.setId(25L);
        otherDiscountView.setBookView(otherBookView);

        // Then
        assertFalse(discountView.equals(otherDiscountView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForDiscountWithDifferentBookView() throws Exception {
        // When
        otherBookView.setId(25L);
        otherDiscountView.setBookView(otherBookView);


        // Then
        assertNotEquals(discountView.hashCode(), otherDiscountView.hashCode());
    }

    @Test
    public void shouldReturnFalseForDiscountWithDifferentBookstoreView() throws Exception {
        // When
        otherBookstoreView.setId(25L);
        otherDiscountView.setBookstoreView(otherBookstoreView);

        // Then
        assertFalse(discountView.equals(otherDiscountView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForDiscountWithDifferentBookstoreView() throws Exception {
        // When
        otherBookstoreView.setId(25L);
        otherDiscountView.setBookstoreView(otherBookstoreView);

        // Then
        assertNotEquals(discountView.hashCode(), otherDiscountView.hashCode());
    }
}