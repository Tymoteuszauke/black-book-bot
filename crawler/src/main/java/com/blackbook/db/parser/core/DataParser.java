package com.blackbook.db.parser.core;

import com.blackbook.botrest.model.BookCreationData;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface DataParser<T> {

    BookCreationData parseBook(T objectToParse);
    List<BookCreationData> parseBooks(T objectToParse);
}
