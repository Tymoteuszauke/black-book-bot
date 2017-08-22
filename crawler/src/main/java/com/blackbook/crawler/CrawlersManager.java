package com.blackbook.crawler;

import com.blackbook.crawler.core.CrawlerActionListener;
import com.blackbook.crawler.core.ICrawler;
import com.blackbook.crawler.core.ICrawlersManager;
import com.blackbook.crawler.db.CrawlerBooksRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */

public class CrawlersManager implements ICrawlersManager {

    private final Map<String, ICrawler> crawlers;
    private final CrawlerBooksRepository crawlerBooksRepository;

    public CrawlersManager(CrawlerBooksRepository crawlerBooksRepository) {
        this.crawlerBooksRepository = crawlerBooksRepository;
        this.crawlers = new HashMap<>();
    }

    @Override
    public void startCrawler(String crawlerId) {
        getCrawlerById(crawlerId).start(crawlerBooksRepository, new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {

            }

            @Override
            public void crawlerFinished(String crawlerId) {

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
        crawlers.forEach((key, crawler) ->  crawler.start(crawlerBooksRepository, new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {

            }

            @Override
            public void crawlerFinished(String crawlerId) {

            }
        }));
    }
}
