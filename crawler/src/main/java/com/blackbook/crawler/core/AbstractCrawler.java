package com.blackbook.crawler.core;


import com.blackbook.botrest.model.BookCreationData;
import com.blackbook.db.DBWriter;

import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public abstract class AbstractCrawler implements ICrawler{

    private final DBWriter dbWriter;

    public AbstractCrawler() {
        dbWriter = new DBWriter();
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
