package com.blackbook.crawler.core;

import com.blackbook.crawler.db.CrawlerBooksRepository;
import com.blackbook.crawler.db.model.BookCreationData;

import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(CrawlerBooksRepository crawlerBooksRepository, CrawlerActionListener actionListener);
    String getId();
    String getBaseUrl();
    String getRequest(int page, int numberOfItemsOnPage);
    String getCriteria();
    void saveToDB(BookCreationData bookCreationData);
    void saveToDBAll(List<BookCreationData> dataList);

}
