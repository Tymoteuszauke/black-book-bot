package com.blackbook.persistencebot.util;

import com.blackbook.persistencebot.model.Author;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import view.book_discount.BookAuthorView;
import view.book_discount.BookView;
import view.book_discount.BookstoreView;
import view.book_discount.BookDiscountView;

import java.util.stream.Collectors;

public class ViewMapperUtil {

    public static BookDiscountView bookDiscountViewConverter(BookDiscount bookDiscount) {
        BookDiscountView bookDiscountView = new BookDiscountView();
        bookDiscountView.setId(bookDiscount.getId());
        bookDiscountView.setPrice(bookDiscount.getPrice());
        if (bookDiscount.getBookstore() != null) {
            bookDiscountView.setBookstoreView(ViewMapperUtil.bookStoreViewFromBookstore(bookDiscount.getBookstore()));
        }
        bookDiscountView.setDiscountDetails(bookDiscount.getBookDiscountDetails());
        bookDiscountView.setBookView(ViewMapperUtil.bookViewFromBook(bookDiscount.getBook()));
        return bookDiscountView;
    }

    private static BookView bookViewFromBook(Book book) {
        BookView bookView = new BookView();
        bookView.setId(book.getId());
        bookView.setTitle(book.getTitle());
        bookView.setSubtitle(book.getSubtitle());
        if (book.getAuthors() != null) {
            bookView.setAuthors(book
                    .getAuthors()
                    .stream()
                    .map(ViewMapperUtil::bookAuthorViewFromAuthor)
                    .collect(Collectors.toSet()));
        }
        return bookView;
    }

    private static BookAuthorView bookAuthorViewFromAuthor(Author author) {
        BookAuthorView bookAuthorView = new BookAuthorView();
        bookAuthorView.setName(author.getName());
        bookAuthorView.setSurname(author.getSurname());
        return bookAuthorView;
    }

    private static BookstoreView bookStoreViewFromBookstore(Bookstore bookstore) {
        BookstoreView bookstoreView = new BookstoreView();
        bookstoreView.setId(bookstore.getId());
        bookstoreView.setName(bookstore.getName());
        bookstoreView.setDetails(bookstore.getDetails());
        return bookstoreView;
    }
}
