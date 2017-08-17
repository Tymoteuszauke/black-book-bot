package com.blackbook.crowler.impl;

import com.blackbook.botrest.model.BookCreationData;
import com.blackbook.crowler.core.AbstractCrawler;
import com.blackbook.crowler.core.CrawlerActionListener;
import com.blackbook.crowler.core.KeyAccess;
import com.blackbook.crowler.paginator.core.Paginator;
import com.blackbook.processor.CrawlerProcessor;
import com.blackbook.processor.CrawlerProcessorListener;

import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public class ISBNdbCrawler extends AbstractCrawler implements KeyAccess{

    private final String BASE_URL = "http://isbndb.com/api/v2/json";
    private final String CRITERIA = "books?q=science";

    private final String devKey;
    private final CrawlerProcessor processor;

    public ISBNdbCrawler(String devKey, CrawlerProcessor crawlerProcessor) {
        super();
        this.devKey = devKey;
        this.processor = crawlerProcessor;
        processor.initProcessor(getRequest());
    }

    @Override
    public void start(CrawlerActionListener actionListener) {
        processor.process(new CrawlerProcessorListener() {
            @Override
            public void success(List<BookCreationData> booksData, Paginator paginator) {
                saveToDBAll(booksData);
            }

            @Override
            public void failed(String message) {

            }
        });
    }

    @Override
    public String getId() {
        return ISBNdbCrawler.class.getSimpleName();
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public String getRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getBaseUrl()).append("/");
        builder.append(getKey()).append("/");
        builder.append(getCriteria());
        return builder.toString();
    }

    @Override
    public String getCriteria() {
        return CRITERIA;
    }

    @Override
    public CrawlerProcessor getProcessor() {
        return processor;
    }

    @Override
    public String getKey() {
        return devKey;
    }
}
