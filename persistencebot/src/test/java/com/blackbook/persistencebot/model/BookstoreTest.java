package com.blackbook.persistencebot.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class BookstoreTest {

    private Bookstore bookstore1;
    private Bookstore bookstore2;
    private Bookstore bookstore3;
    private List<BookDiscount> discounts;
    private Book book1;

    @BeforeMethod
    public void before() {
        book1 = new Book();
        book1.setTitle("Pan Tymek");
        book1.setSubtitle("Tymke");
        book1.setGenre("Biografia");
        book1.setAuthors("Tymke Wergiliusz");
        book1.setBookPageUrl("www.bookstore.com/book/pan-tymek");
        book1.setCoverUrl("www.covers.com/tymek");

        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setId(10L);
        bookDiscount.setPrice(25.99);
        bookDiscount.setBookDiscountDetails("-30%");
        bookDiscount.setBook(book1);

        discounts = new ArrayList<>();
        discounts.add(bookDiscount);

        bookstore1 = new Bookstore();
        bookstore1.setId(2L);
        bookstore1.setName("Matras");
        bookstore1.setDetails("Cheap books");
        bookstore1.setBookDiscounts(discounts);

        bookstore2 = new Bookstore();
        bookstore2.setId(2L);
        bookstore2.setName("Matras");
        bookstore2.setDetails("Cheap books");
        bookstore2.setBookDiscounts(discounts);

        bookstore3 = new Bookstore();
        bookstore3.setId(2L);
        bookstore3.setName("Matras");
        bookstore3.setDetails("Very cheap books");
        bookstore3.setBookDiscounts(discounts);
    }

    @Test
    public void testEquals() throws Exception {
        // Then
        assertTrue(bookstore1.equals(bookstore2));
        assertFalse(bookstore1.equals(bookstore3));
        assertFalse(bookstore1.equals(null));
        assertFalse(bookstore1.equals(new Book()));
    }

    @Test
    public void testHashCode() throws Exception {
        // Then
        assertEquals(bookstore1.hashCode(), bookstore2.hashCode());
        assertNotEquals(bookstore1.hashCode(), bookstore3.hashCode());
    }
}