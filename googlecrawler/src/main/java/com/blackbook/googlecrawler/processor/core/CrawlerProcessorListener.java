package com.blackbook.googlecrawler.processor.core;


import com.blackbook.googlecrawler.paginator.core.Paginator;
import view.creationmodel.BookDiscountData;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface CrawlerProcessorListener {
    void success(List<BookDiscountData> bookData, Paginator paginator);
    void failed(String message);
}
