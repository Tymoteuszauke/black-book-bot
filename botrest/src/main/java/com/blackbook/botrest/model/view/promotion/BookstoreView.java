package com.blackbook.botrest.model.view.promotion;

import com.blackbook.dao.model.Bookstore;
import lombok.Data;

@Data
public class BookstoreView {

    private long id;
    private String name;
    private String details;

    public static BookstoreView fromBookstore(Bookstore bookstore) {
        BookstoreView bookstoreView = new BookstoreView();
        bookstoreView.setId(bookstore.getId());
        bookstoreView.setName(bookstore.getName());
        bookstoreView.setDetails(bookstore.getDetails());
        return bookstoreView;
    }
}
