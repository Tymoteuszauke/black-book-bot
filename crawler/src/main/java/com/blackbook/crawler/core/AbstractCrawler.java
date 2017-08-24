package com.blackbook.crawler.core;


import com.blackbook.crawler.db.CrawlerBooksRepository;
import com.blackbook.crawler.db.DBWriter;
import com.blackbook.crawler.db.model.BookCreationData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public abstract class AbstractCrawler implements ICrawler{

    private DBWriter dbWriter;

    public AbstractCrawler() {
        dbWriter = new DBWriter();
    }

    protected void setCrawlerBooksRepository(CrawlerBooksRepository crawlerBooksRepository){
        dbWriter.setBooksRepository(crawlerBooksRepository);
    }

    @Override
    public void saveToDB(BookCreationData bookCreationData) {
        dbWriter.write(bookCreationData);
    }

    @Override
    public void saveToDBAll(List<BookCreationData> dataList) {
        dbWriter.writeAll(dataList);
    }

}
