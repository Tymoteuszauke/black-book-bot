package com.blackbook.crawler.core;

import com.blackbook.crawler.db.CrawlerBooksRepository;
import com.blackbook.crawler.db.model.BookCreationData;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(ExecutorService executorService, CrawlerBooksRepository crawlerBooksRepository, CrawlerActionListener actionListener);
    String getId();
    String getBaseUrl();
    String getRequest(int page, int numberOfItemsOnPage);
    String getCriteria();
    void saveToDB(BookCreationData bookCreationData);
    void saveToDBAll(List<BookCreationData> dataList);

}
