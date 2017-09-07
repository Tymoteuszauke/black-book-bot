package com.blackbook.googlecrawler.impl;

import com.blackbook.googlecrawler.core.CrawlerActionListener;
import com.blackbook.googlecrawler.core.ICrawler;
import com.blackbook.googlecrawler.core.KeyAccess;
import com.blackbook.googlecrawler.paginator.core.Paginator;
import com.blackbook.googlecrawler.processor.ResultModel;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import com.blackbook.googlecrawler.processor.impl.FirstPageGoogleProcessor;
import com.blackbook.googlecrawler.processor.impl.GoogleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

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
    private CrawlerActionListener actionListener;

    private int completedPages;
    private Paginator firstPaginator;
    private final Lock crawlerLock;

    public GoogleCrawler() {
        booksData = new LinkedList<>();
        executorService = Executors.newCachedThreadPool();
        crawlerLock = new ReentrantLock();
    }

    @Override
    public void start(CrawlerActionListener actionListener) {
        this.actionListener = actionListener;
        CrawlerProcessorListener firstProcessorListener = new CrawlerProcessorListener() {
            @Override
            public void success(Supplier<ResultModel> resultModelSupplier) {
                addBooksToResultList(resultModelSupplier.get().getBookData());
                firstPaginator = resultModelSupplier.get().getPaginator();

                if (isFinished()){
                    finishCrawler();
                } else {
                    sendRestOfResponses(firstPaginator.getItemsOnPage(), firstPaginator.getTotalNumberOfItems());
                }
            }

            @Override
            public void failed(String message) {
                log.warn("First page request failed. Crawler id: " + getId() + " Reason is: " + message);
            }
        };

        executorService.execute(new FirstPageGoogleProcessor(getRequest(0, NUMBER_BOOKS_ON_PAGE), firstProcessorListener));
    }

    private void sendRestOfResponses(int itemOnPage, int totalItems) {
        int position = itemOnPage;

        CrawlerProcessorListener processorListener = new CrawlerProcessorListener() {
            @Override
            public void success(Supplier<ResultModel> resultModelSupplier) {
                addBooksToResultList(resultModelSupplier.get().getBookData());
                if (isFinished()){
                    finishCrawler();
                }
            }

            @Override
            public void failed(String message) {
                log.warn("Page request failed. Crawler id: " + getId() + " Reason is: " + message);
            }
        };

        while (position <= totalItems) {
            executorService.execute(new GoogleProcessor(getRequest(position, NUMBER_BOOKS_ON_PAGE), processorListener));
            position += itemOnPage + 1;
        }
    }

    private void addBooksToResultList(List<BookDiscountData> newBooksList) {
        booksData.addAll(newBooksList);
        try {
            if (crawlerLock.tryLock(2, TimeUnit.SECONDS)) {
                completedPages += 1;
            }
        } catch (InterruptedException e) {
            log.warn("completedPages was not increased!");
        } finally {
            crawlerLock.unlock();
        }

    }

    private boolean isFinished() {
        return completedPages == firstPaginator.getNumberOfPages();
    }

    private void finishCrawler() {
        log.info("Google crawler finished, found [" + booksData.size() + "] books");
        actionListener.crawlerFinished(booksData);

        log.info("Terminating executor.");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
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
