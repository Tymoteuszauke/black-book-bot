package com.blackbook.googlecrawler.parser.core;


import view.creationmodel.BookDiscountData;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface DataParser<T> {

    BookDiscountData parseBook(T objectToParse);
    List<BookDiscountData> parseBooks(T objectToParse);
}
