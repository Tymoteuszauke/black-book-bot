package com.blackbook.botpersistence.util;

import com.blackbook.botpersistence.model.Author;
import com.blackbook.botpersistence.model.Book;
import com.blackbook.botpersistence.model.Bookstore;
import com.blackbook.botpersistence.model.Promotion;
import view.promotion.BookAuthorView;
import view.promotion.BookView;
import view.promotion.BookstoreView;
import view.promotion.PromotionView;

import java.util.stream.Collectors;

public class MappingUtil {

    public static BookView BookViewFromBook(Book book) {
        BookView bookView = new BookView();
        bookView.setId(book.getId());
        bookView.setTitle(book.getTitle());
        bookView.setSubtitle(book.getSubtitle());
        if (book.getAuthors() != null) {
            bookView.setAuthors(book
                    .getAuthors()
                    .stream()
                    .map(MappingUtil::BookAuthorViewFromAuthor)
                    .collect(Collectors.toSet()));
        }
        return bookView;
    }

    public static PromotionView PromotionViewFromPromotion(Promotion promotion) {
        PromotionView promotionView = new PromotionView();
        promotionView.setId(promotion.getId());
        promotionView.setPrice(promotion.getPrice());
        promotionView.setBookstoreView(MappingUtil.BookStoreViewFromBookstore(promotion.getBookstore()));
        promotionView.setPromotionDetails(promotion.getPromotionDetails());
        promotionView.setBookView(MappingUtil.BookViewFromBook(promotion.getBook()));
        return promotionView;
    }

    private static BookAuthorView BookAuthorViewFromAuthor(Author author) {
        BookAuthorView bookAuthorView = new BookAuthorView();
        bookAuthorView.setName(author.getName());
        bookAuthorView.setSurname(author.getSurname());
        return bookAuthorView;
    }

    private static BookstoreView BookStoreViewFromBookstore(Bookstore bookstore) {
        BookstoreView bookstoreView = new BookstoreView();
        bookstoreView.setId(bookstore.getId());
        bookstoreView.setName(bookstore.getName());
        bookstoreView.setDetails(bookstore.getDetails());
        return bookstoreView;
    }
}
