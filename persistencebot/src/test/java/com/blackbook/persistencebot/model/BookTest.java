package com.blackbook.persistencebot.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BookTest {

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeMethod
    public void before() {
        book1 = new Book();
        book1.setTitle("Pan Tymek");
        book1.setSubtitle("Tymke");
        book1.setGenre("Biografia");
        book1.setAuthors("Tymke Wergiliusz");
        book1.setBookPageUrl("www.bookstore.com/book/pan-tymek");
        book1.setCoverUrl("www.covers.com/tymek");

        book2 = new Book();
        book2.setTitle("Pan Tymek");
        book2.setSubtitle("Tymke");
        book2.setGenre("Biografia");
        book2.setAuthors("Tymke Wergiliusz");
        book2.setBookPageUrl("www.bookstore.com/book/pan-tymek");
        book2.setCoverUrl("www.covers.com/tymek");

        book3 = new Book();
        book3.setTitle("Pan Tymke");
        book3.setSubtitle("Tymek");
        book3.setGenre("Biografia");
        book3.setAuthors("Tymke Wergiliusz");
        book3.setBookPageUrl("www.bookstore.com/book/pan-tymek");
        book3.setCoverUrl("www.covers.com/tymek");
    }

    @Test
    public void testEquals() throws Exception {
        // Then
        assertTrue(book1.equals(book2));
        assertFalse(book1.equals(book3));
        assertFalse(book1.equals(null));
        assertFalse(book1.equals(new BookDiscount()));
    }

    @Test
    public void testHashCode() throws Exception {
        // Then
        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }
}