package com.blackbook.botrest.model.view.book;

import com.blackbook.botrest.model.Author;
import lombok.Data;

/**
 * Created by tymek on 25.08.17.
 */
@Data
public class BookAuthorView {
    private String name;
    private String surname;

    public static BookAuthorView fromAuthor(Author author) {
        BookAuthorView bookAuthorView = new BookAuthorView();
        bookAuthorView.setName(author.getName());
        bookAuthorView.setSurname(author.getSurname());
        return bookAuthorView;
    }
}
