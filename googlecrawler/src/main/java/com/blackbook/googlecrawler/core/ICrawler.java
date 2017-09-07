package com.blackbook.googlecrawler.core;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(CrawlerActionListener actionListener);
    String getId();
    String getBaseUrl();
    String getRequest(int page, int numberOfItemsOnPage);
    String getCriteria();


}
