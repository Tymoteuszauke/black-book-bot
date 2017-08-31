package com.blackbook.persistencebot.util;

import com.blackbook.persistencebot.model.Author;
import com.blackbook.persistencebot.model.Book;
import com.blackbook.persistencebot.model.Bookstore;
import com.blackbook.persistencebot.model.Promotion;
import view.promotion.BookAuthorView;
import view.promotion.BookView;
import view.promotion.BookstoreView;
import view.promotion.PromotionView;

import java.util.stream.Collectors;

public class ViewMapperUtil {

    public static BookView bookViewFromBook(Book book) {
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

    public static PromotionView promotionViewFromPromotion(Promotion promotion) {
        PromotionView promotionView = new PromotionView();
        promotionView.setId(promotion.getId());
        promotionView.setPrice(promotion.getPrice());
        if (promotion.getBookstore() != null) {
            promotionView.setBookstoreView(ViewMapperUtil.bookStoreViewFromBookstore(promotion.getBookstore()));
        }
        promotionView.setPromotionDetails(promotion.getPromotionDetails());
        promotionView.setBookView(ViewMapperUtil.bookViewFromBook(promotion.getBook()));
        return promotionView;
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
