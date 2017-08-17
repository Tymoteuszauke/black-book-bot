package com.blackbook.crowler.core;

import com.blackbook.botrest.model.BookCreationData;
import com.blackbook.processor.CrawlerProcessor;

import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(CrawlerActionListener actionListener);
    String getId();
    String getBaseUrl();
    String getRequest();
    String getCriteria();
    CrawlerProcessor getProcessor();
    void saveToDB(BookCreationData bookCreationData);
    void saveToDBAll(List<BookCreationData> dataList);
}
