package com.blackbook.crowler.core;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(CrawlerActionListener actionListener);
    void finish(CrawlerActionListener actionListener);
    String getId();
    String getBaseUrl();
    String getRequest();
    String getCriteria();
}
