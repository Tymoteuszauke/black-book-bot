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
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GoogleCrawler extends AbstractCrawler implements KeyAccess{

    private final String BASE_URL = "https://www.googleapis.com/books/v1";
    private final String KEY_STRING = "key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private final String CRITERIA = "";

    private final CrawlerProcessor processor;

    public GoogleCrawler(CrawlerProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void start(CrawlerActionListener actionListener) {
        processor.process(new CrawlerProcessorListener() {
            @Override
            public void success(List<BookCreationData> bookData, Paginator paginator) {

            }

            @Override
            public void failed(String message) {

            }
        });
    }

    @Override
    public String getId() {
        return getClass().getSimpleName();
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public String getRequest() {
        StringBuilder builder = new StringBuilder();
        builder.append(getBaseUrl()).append("/");
        builder.append(getCriteria()).append("/");
        builder.append(getKey());
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
        return KEY_STRING;
    }
}
