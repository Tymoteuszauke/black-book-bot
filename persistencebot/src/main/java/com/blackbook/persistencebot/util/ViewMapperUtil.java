package com.blackbook.persistencebot.util;

import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.persistencebot.model.Genre;
import com.blackbook.utils.model.view.BookDiscountView;
import com.blackbook.utils.model.view.BookView;
import com.blackbook.utils.model.view.BookstoreView;
import com.blackbook.utils.model.view.GenreView;

import java.util.List;
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
        bookView.setPublisher(book.getPublisher());
        bookView.setAuthors(book.getAuthors());
        bookView.setBookPageUrl(book.getBookPageUrl());
        bookView.setCoverUrl(book.getCoverUrl());
        bookView.setGenreView(genreViewsFromGenres(book.getGenres()));
        return bookView;
    }

    private static List<GenreView> genreViewsFromGenres(List<Genre> genres) {
        return genres
                .stream()
                .map(genre -> new GenreView(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
    }

    private static BookstoreView bookStoreViewFromBookstore(Bookstore bookstore) {
        BookstoreView bookstoreView = new BookstoreView();
        bookstoreView.setId(bookstore.getId());
        bookstoreView.setName(bookstore.getName());
        bookstoreView.setDetails(bookstore.getDetails());
        return bookstoreView;
    }
}
