package com.blackbook.googlecrawler.impl;

import com.blackbook.googlecrawler.core.KeyAccess;
import com.blackbook.googlecrawler.paginator.core.Paginator;
import com.blackbook.googlecrawler.processor.ResultModel;
import com.blackbook.googlecrawler.processor.core.CrawlerProcessorListener;
import com.blackbook.googlecrawler.processor.impl.FirstPageGoogleProcessor;
import com.blackbook.googlecrawler.processor.impl.GoogleProcessor;
import com.blackbook.utils.core.ICrawler;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import lombok.extern.slf4j.Slf4j;
import view.creationmodel.BookDiscountData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.blackbook.googlecrawler.paginator.impl.GooglePaginator.NUMBER_BOOKS_ON_PAGE;

/**
 * @author Siarhei Shauchenka
 * @since 17.08.17
 */
@Slf4j
public class GoogleCrawler implements ICrawler, KeyAccess {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String KEY_STRING = "&key=AIzaSyD5fIReicRyjqkK-TKO5akZ2Uw2v_Qhs_4";
    private static final String CRITERIA = "-";

    public static final int GOOGLE_CRAWLER_ID = 4;

    private final List<BookDiscountData> booksData;
    private Consumer<List<BookDiscountData>> consumer;

    private int completedPages;
    private final Lock crawlerLock;

    private ExecutorService executorService;

    public GoogleCrawler(ExecutorService executorService) {
        this.executorService = executorService;
        booksData = new LinkedList<>();
        crawlerLock = new ReentrantLock();
    }

    @Override
    public void start(final Consumer<List<BookDiscountData>> consumer) {
        this.completedPages = 0;

        CrawlerProcessorListener firstProcessorListener = new CrawlerProcessorListener() {
            @Override
            public void success(Supplier<ResultModel> resultModelSupplier) {
                addBooksToResultList(resultModelSupplier.get().getBookData());
                Paginator firstPaginator = resultModelSupplier.get().getPaginator();

                if (isFinished(firstPaginator)) {
                    finishCrawler(consumer);
                } else {
                    sendRestOfResponses(firstPaginator, consumer);
                }
            }

            @Override
            public void failed(String message) {
                addBooksToResultList(Collections.emptyList());
                finishCrawler(consumer);
                log.warn("First page request failed. Crawler id: " + getId() + " Reason is: " + message);
            }
        };

        executorService.execute(new FirstPageGoogleProcessor(getRequest(0, NUMBER_BOOKS_ON_PAGE), firstProcessorListener));
    }

    void sendRestOfResponses(Paginator firstpaginator, Consumer<List<BookDiscountData>> consumer) {
        int position = firstpaginator.getItemsOnPage();
        int totalItems = firstpaginator.getTotalNumberOfItems();

        CrawlerProcessorListener processorListener = new CrawlerProcessorListener() {
            @Override
            public void success(Supplier<ResultModel> resultModelSupplier) {
                addBooksToResultList(resultModelSupplier.get().getBookData());
                checkIfFinished(consumer, firstpaginator);
            }

            @Override
            public void failed(String message) {
                log.warn("Page request failed. Crawler id: " + getId() + " Reason is: " + message);
                addBooksToResultList(Collections.emptyList());
                checkIfFinished(consumer, firstpaginator);
            }
        };

        while (position <= totalItems) {
            executorService.execute(new GoogleProcessor(getRequest(position, NUMBER_BOOKS_ON_PAGE), processorListener));
            position += firstpaginator.getItemsOnPage() + 1;
        }
    }

    private void checkIfFinished(Consumer<List<BookDiscountData>> consumer, Paginator paginator) {
        if (isFinished(paginator)) {
            finishCrawler(consumer);
        }
    }

    void addBooksToResultList(List<BookDiscountData> newBooksList) {
        try {
            if (crawlerLock.tryLock(2, TimeUnit.SECONDS)) {
                getBooksData().addAll(newBooksList);
                completedPages += 1;
            }
        } catch (InterruptedException e) {
            log.warn("data was not added!");
            Thread.currentThread().interrupt();
        } finally {
            crawlerLock.unlock();
        }

    }

    private boolean isFinished(Paginator paginator) {
        return completedPages == paginator.getNumberOfPages();
    }

    void finishCrawler(Consumer<List<BookDiscountData>> consumer) {
        log.info("Google crawler finished, found [" + getBooksData().size() + "] books");
        consumer.accept(booksData);
    }

    @Override
    public int getId() {
        return GOOGLE_CRAWLER_ID;
    }


    String getBaseUrl() {
        return BASE_URL;
    }


    String getRequest(int startPosition, int numberOfItemsOnPage) {
        StringBuilder builder = new StringBuilder();
        builder.append(getBaseUrl());
        builder.append(getCriteria());
        builder.append(getKey());
        builder.append("&startIndex=").append(startPosition);
        builder.append("&maxResults=").append(numberOfItemsOnPage);
        return builder.toString();
    }

    String getCriteria() {
        return CRITERIA;
    }

    @Override
    public String getKey() {
        return KEY_STRING;
    }

    public List<BookDiscountData> getBooksData() {
        return booksData;
    }
}
