package com.blackbook.crawler.impl;

import com.blackbook.crawler.core.AbstractCrawler;
import com.blackbook.crawler.core.CrawlerActionListener;
import com.blackbook.crawler.core.KeyAccess;
import com.blackbook.crawler.db.CrawlerBooksRepository;
import com.blackbook.crawler.db.model.BookCreationData;
import com.blackbook.crawler.paginator.core.Paginator;
import com.blackbook.crawler.processor.core.CrawlerProcessorListener;
import com.blackbook.crawler.processor.impl.GoogleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
@Slf4j
@Service
public class GoogleCrawler extends AbstractCrawler implements KeyAccess {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String KEY_STRING = "&key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private final String CRITERIA = "-";
    private final int NUMBER_OF_ITEMS_ON_PAGE = 40;

    @Override
    public void start(ExecutorService executorService, CrawlerBooksRepository crawlerBooksRepository, CrawlerActionListener actionListener) {
        setCrawlerBooksRepository(crawlerBooksRepository);
        startFirstRequest(executorService, actionListener);
    }

    private void startFirstRequest(ExecutorService executorService, CrawlerActionListener actionListener) {
        actionListener.crawlerStarted(getId());
        GoogleProcessor firstProcessor = new GoogleProcessor(getRequest(0, NUMBER_OF_ITEMS_ON_PAGE), 1, new CrawlerProcessorListener() {
            @Override
            public void success(List<BookCreationData> bookData, Paginator paginator) {
                saveToDBAll(bookData);
                sendRestOfResponses(executorService, paginator, actionListener);
            }

            @Override
            public void failed(String message) {
                log.warn("First page request failed. Crawler id: " + getId() + " Reason is: " + message);
            }
        });
        executorService.execute(firstProcessor);
    }

    private void sendRestOfResponses(ExecutorService executorService, Paginator firsPaginator, CrawlerActionListener actionListener) {
        int position = firsPaginator.getItemsOnPage();
        for (int i = 1; i <= firsPaginator.getNumberOfPages(); i++) {
            GoogleProcessor processor = new GoogleProcessor(getRequest(position, firsPaginator.getItemsOnPage()), i, new CrawlerProcessorListener() {
                @Override
                public void success(List<BookCreationData> bookData, Paginator paginator) {
                    saveToDBAll(bookData);
                    if (paginator.getCurrentPage() == firsPaginator.getNumberOfPages()) {
                        actionListener.crawlerFinished(getId());
                        // this lof for debug remove need to be removed
//                        log.info("Crawler [" + getId() + "] finished. Total pages: " + firsPaginator.getNumberOfPages() + " current page: " + paginator.getCurrentPage());
                    }
                }

                @Override
                public void failed(String message) {
                    log.warn("Page request failed. Crawler id: " + getId() + " Reason is: " + message);
                }
            });
            position += firsPaginator.getItemsOnPage();
            executorService.execute(processor);
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
