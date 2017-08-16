package com.blackbook.crowler;

import com.blackbook.crowler.core.CrawlerActionListener;
import com.blackbook.crowler.core.ICrawler;
import com.blackbook.crowler.core.ICrawlersManager;

import java.util.*;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */

public class CrawlersManager implements ICrawlersManager {

    private final Map<String, ICrawler> crawlers;

    public CrawlersManager() {
        this.crawlers = new HashMap<>();
    }

    @Override
    public void startCrawler(String crawlerId, CrawlerActionListener actionListener) {
        getCrawlerById(crawlerId).start(actionListener);
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
    public void startAll(CrawlerActionListener actionListener) {
        crawlers.forEach((key, crawler) ->  crawler.start(actionListener));
    }
}
