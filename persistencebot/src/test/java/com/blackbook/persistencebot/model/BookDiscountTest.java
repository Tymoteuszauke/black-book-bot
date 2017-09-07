package com.blackbook.persistencebot.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BookDiscountTest {

    private BookDiscount discount1;
    private BookDiscount discount2;
    private BookDiscount discount3;
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

        discount1 = new BookDiscount();
        discount1.setId(10L);
        discount1.setPrice(25.99);
        discount1.setBookDiscountDetails("-30%");
        discount1.setBook(book1);

        discount2 = new BookDiscount();
        discount2.setId(10L);
        discount2.setPrice(25.99);
        discount2.setBookDiscountDetails("-30%");
        discount2.setBook(book1);

        discount3 = new BookDiscount();
        discount3.setId(10L);
        discount3.setPrice(25.99);
        discount3.setBookDiscountDetails("-33%");
        discount3.setBook(book1);
    }

    @Test
    public void testEquals() throws Exception {
        // Then
        assertTrue(discount1.equals(discount2));
        assertFalse(discount1.equals(discount3));
    }

    @Test
    public void testHashCode() throws Exception {
    assertEquals(discount1.hashCode(), discount2.hashCode());
    assertNotEquals(discount1.hashCode(), discount3.hashCode());
    }

}