package com.blackbook.crawler;

import com.blackbook.crawler.core.CrawlerActionListener;
import com.blackbook.crawler.core.ICrawler;
import com.blackbook.crawler.core.ICrawlersManager;
import com.blackbook.crawler.db.CrawlerBooksRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
@Slf4j
public class CrawlersManager implements ICrawlersManager {

    private final Map<String, ICrawler> crawlers;
    private final CrawlerBooksRepository crawlerBooksRepository;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private List<String> crawlersForRequest;

    public CrawlersManager(CrawlerBooksRepository crawlerBooksRepository) {
        this.crawlerBooksRepository = crawlerBooksRepository;
        this.crawlers = new HashMap<>();
        this.crawlersForRequest = new ArrayList<>();
    }

    @Override
    public void startCrawler(String crawlerId) {
        if (!crawlersForRequest.contains(crawlerId)){
            crawlersForRequest.add(crawlerId);
        }
        getCrawlerById(crawlerId).start(executorService, crawlerBooksRepository, new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {
                log.info("Crawler " + crawlerId + " has started!");
            }

            @Override
            public void crawlerFinished(String crawlerId) {
                crawlersForRequest.remove(crawlerId);
                if (crawlersForRequest.isEmpty()){
                    //send response to scheduler that crawlers finished
                }
                log.info("Crawler " + crawlerId + " finished");
            }
        });
    }

    @Override
    public List<ICrawler> getCrawlersList() {
        return new ArrayList<>(crawlers.values());
    }

    @Override
    public List<String> getCrawlersId() {
        return new ArrayList<>(crawlers.keySet());
    }

    @Override
    public void addCrawler(ICrawler crawler) {
        crawlers.put(crawler.getId(), crawler);
    }

    @Override
    public void removeCrawler(String crawlerId) {
        crawlers.remove(crawlerId);
    }

    @Override
    public ICrawler getCrawlerById(String id) {
        return crawlers.get(id);
    }

    @Override
    public void startAll() {
        crawlersForRequest.addAll(crawlers.keySet());
        crawlers.forEach((key, crawler) ->  startCrawler(key));
    }

    @Override
    public void close() {
        terminateExecutor();
    }

    private void terminateExecutor() {
        log.info("Trying to terminate executor... ");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
    }
}
