package com.blackbook.botrest.model.view.book;

import com.blackbook.dao.model.Book;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tymek on 25.08.17.
 */
@Data
public class BookView {
    private long id;
    private String title;
    private String subtitle;
    private Set<BookAuthorView> authors;
    private List<BookPromotionView> promotions;

    public static BookView fromBook(Book book) {
        BookView bookView = new BookView();
        bookView.setId(book.getId());
        bookView.setTitle(book.getTitle());
        bookView.setSubtitle(book.getSubtitle());
        if (book.getAuthors() != null) {
            bookView.setAuthors(book
                    .getAuthors()
                    .stream()
                    .map(BookAuthorView::fromAuthor)
                    .collect(Collectors.toSet()));
        }
        if (book.getPromotions() != null) {
            bookView.setPromotions(book
                    .getPromotions()
                    .stream()
                    .map(BookPromotionView::fromPromotion)
                    .collect(Collectors.toList()));
        }
        return bookView;
    }

}
