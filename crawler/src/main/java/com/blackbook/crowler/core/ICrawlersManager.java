package com.blackbook.crowler.core;

import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawlersManager {
    void startCrawler(String crawlerId);
    List<ICrawler> getCrawlersList();
    List<String> getCrawlersId();
    void addCrawler(ICrawler crawler);
    void removeCrawler(String crawlerId);
    ICrawler getCrawlerById(String id);
    void startAll();
}
