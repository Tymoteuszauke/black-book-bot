package com.blackbook.crawler.impl;

import com.blackbook.botrest.model.BookCreationData;
import com.blackbook.crawler.core.AbstractCrawler;
import com.blackbook.crawler.core.CrawlerActionListener;
import com.blackbook.crawler.core.KeyAccess;
import com.blackbook.crawler.paginator.core.Paginator;
import com.blackbook.processor.CrawlerProcessorListener;
import com.blackbook.processor.impl.GoogleProcessor;

import java.util.List;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
public class GoogleCrawler extends AbstractCrawler implements KeyAccess {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String KEY_STRING = "&key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private final String CRITERIA = "flowers+inauthor:keyes";


    @Override
    public void start(CrawlerActionListener actionListener) {
        startFirstRequest(actionListener);
    }

    private void startFirstRequest(CrawlerActionListener actionListener) {
        GoogleProcessor firstProcessor = new GoogleProcessor(getRequest(0, 20), 1);
        firstProcessor.process(new CrawlerProcessorListener() {
            @Override
            public void success(List<BookCreationData> bookData, Paginator paginator) {
                saveToDBAll(bookData);
                sendRestOfResponses(paginator, actionListener);
            }

            @Override
            public void failed(String message) {

            }
        });
    }

    private void sendRestOfResponses(Paginator firsPaginator, CrawlerActionListener actionListener) {
        int position = firsPaginator.getItemsOnPage();
        for (int i = 0; i < firsPaginator.getNumberOfPages(); i++) {
            GoogleProcessor processor = new GoogleProcessor(getRequest(position, firsPaginator.getItemsOnPage()), i);
            processor.process(new CrawlerProcessorListener() {
                @Override
                public void success(List<BookCreationData> bookData, Paginator paginator) {
                    saveToDBAll(bookData);
                    if (paginator.getCurrentPage() == firsPaginator.getNumberOfPages()){
                        actionListener.crawlerFinished(getId());
                    }
                }

                @Override
                public void failed(String message) {

                }
            });
            position += firsPaginator.getItemsOnPage() + 1;
        }
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
    public String getRequest(int startPosition, int numberOfItemsOnPage) {
        StringBuilder builder = new StringBuilder();
        builder.append(getBaseUrl());
        builder.append(getCriteria());
        builder.append(getKey());
        builder.append("&startIndex=").append(startPosition);
        builder.append("&maxResults=").append(numberOfItemsOnPage);
        return builder.toString();
    }

    @Override
    public String getCriteria() {
        return CRITERIA;
    }


    @Override
    public String getKey() {
        return KEY_STRING;
    }
}
