package com.blackbook.processor;

import com.blackbook.botrest.model.BookCreationData;
import com.blackbook.crawler.paginator.core.Paginator;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public interface CrawlerProcessorListener {
    void success(List<BookCreationData> bookData, Paginator paginator);
    void failed(String message);
}
