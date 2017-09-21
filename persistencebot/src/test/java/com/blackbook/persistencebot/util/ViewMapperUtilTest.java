package com.blackbook.persistencebot.util;

import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.utils.model.view.BookDiscountView;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ViewMapperUtilTest {

    @DataProvider
    private Object[][] bookDiscountProvider() {
        return new Object[][]{
                {getDiscountWithBookstore()},
                {getDiscountWithoutBookstore()}
        };
    }

    @Test(dataProvider = "bookDiscountProvider")
    public void shouldConvertDiscountWihBookstoreToView(BookDiscount bookDiscount) throws Exception {
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

    private BookDiscount getDiscountWithBookstore() {
        Book book = new Book();
        book.setId(12L);
        book.setTitle("Pan Tymek");
        book.setSubtitle("-");
        book.setAuthors("Tymke Wergiliusz");
//        book.setGenre("Biografia");
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
        return bookDiscount;
    }

    private BookDiscount getDiscountWithoutBookstore() {
        Book book = new Book();
        book.setId(12L);
        book.setTitle("Pan Tymek");
        book.setSubtitle("-");
        book.setAuthors("Tymke Wergiliusz");
//        book.setGenre("Biografia");
        book.setCoverUrl("www.covers.com/tymek");
        book.setBookPageUrl("www.bookstore.com/book/pan-tymek");

        BookDiscount bookDiscount = new BookDiscount();
        bookDiscount.setId(25L);
        bookDiscount.setPrice(25.99);
        bookDiscount.setBookDiscountDetails("-25%");
        bookDiscount.setBook(book);
        bookDiscount.setBookstore(null);
        return bookDiscount;
    }
}