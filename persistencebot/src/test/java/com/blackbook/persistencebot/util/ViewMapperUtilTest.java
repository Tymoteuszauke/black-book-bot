package com.blackbook.persistencebot.util;

import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import org.testng.Assert;
import org.testng.annotations.Test;
import view.bookdiscount.BookDiscountView;

import static org.testng.Assert.*;

public class ViewMapperUtilTest {

    @Test
    public void shouldConvertDiscountToView() throws Exception {
        // Given
        Book book = new Book();
        book.setId(12L);
        book.setTitle("Pan Tymek");
        book.setSubtitle("-");
        book.setAuthors("Tymke Wergiliusz");
        book.setGenre("Biografia");
        book.setCoverUrl("www.covers.com/tymek");
        book.setBookPageUrl("www.bookstore.com/book/pan-tymek");

        Bookstore bookstore = new Bookstore();
        bookstore.setId(2L);
        bookstore.setName("Tania księgarnia");
        bookstore.setDetails("Kup pan książkę");

        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setId(25L);
        bookDiscount.setPrice(25.99);
        bookDiscount.setBookDiscountDetails("-25%");
        bookDiscount.setBook(book);
        bookDiscount.setBookstore(bookstore);

        // When
        BookDiscountView view = ViewMapperUtil.bookDiscountViewConverter(bookDiscount);

        // Then
        assertEquals(25L, view.getId());
        assertEquals(25.99, view.getPrice());
        assertEquals("-25%", view.getDiscountDetails());

        assertEquals(12L, view.getBookView().getId());
        assertEquals("Pan Tymek", view.getBookView().getTitle());
        assertEquals("-", view.getBookView().getSubtitle());
        assertEquals("Tymke Wergiliusz", view.getBookView().getAuthors());
        assertEquals("www.covers.com/tymek", view.getBookView().getCoverUrl());
        assertEquals("www.bookstore.com/book/pan-tymek", view.getBookView().getBookPageUrl());
    }
}