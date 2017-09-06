package com.blackbook.googlecrawler.impl;

import com.blackbook.googlecrawler.core.CrawlerActionListener;
import com.blackbook.googlecrawler.core.ICrawler;
import com.blackbook.googlecrawler.core.KeyAccess;
import com.blackbook.googlecrawler.paginator.core.Paginator;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import com.blackbook.googlecrawler.processor.impl.GoogleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.blackbook.googlecrawler.paginator.impl.GooglePaginator.NUMBER_BOOKS_ON_PAGE;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
@Slf4j
@Service
public class GoogleCrawler implements ICrawler, KeyAccess {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String KEY_STRING = "&key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private static final String CRITERIA = "-";

    private final List<BookDiscountData> booksData;
    private final ExecutorService executorService;

    private int compleatedPages;

    public GoogleCrawler() {
        booksData = new LinkedList<>();
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void start(CrawlerActionListener actionListener) {
        startFirstRequest(actionListener);
    }

    private void startFirstRequest(CrawlerActionListener actionListener) {
        GoogleProcessor firstProcessor = new GoogleProcessor(getRequest(0, NUMBER_BOOKS_ON_PAGE), 1, new CrawlerProcessorListener() {
            @Override
            public void success(List<BookDiscountData> bookData, Paginator paginator) {
                booksData.addAll(bookData);
                compleatedPages +=1;
                sendRestOfResponses(paginator, actionListener);
            }

            @Override
            public void failed(String message) {
                log.warn("First page request failed. Crawler id: " + getId() + " Reason is: " + message);
            }
        });
        executorService.execute(firstProcessor);
    }

    private void sendRestOfResponses(Paginator firsPaginator, CrawlerActionListener actionListener) {
        int position = firsPaginator.getItemsOnPage();

        for (int i = 1; i <= firsPaginator.getNumberOfPages(); i++) {
            GoogleProcessor processor = new GoogleProcessor(getRequest(position, firsPaginator.getItemsOnPage()), i, new CrawlerProcessorListener() {
                @Override
                public void success(List<BookDiscountData> bookData, Paginator paginator) {
                    booksData.addAll(bookData);
                    compleatedPages +=1;
                    if (compleatedPages == firsPaginator.getNumberOfPages()){
                        log.info("Google crawler finished, found [" + booksData.size() + "] books");
                        actionListener.crawlerFinished(booksData);
                    }
                }

                @Override
                public void failed(String message) {
                    log.warn("Page request failed. Crawler id: " + getId() + " Reason is: " + message);
                }
            });
            position += firsPaginator.getItemsOnPage()+1;
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
