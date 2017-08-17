package com.blackbook.crowler.core;

import com.blackbook.processor.CrawlerProcessor;
import org.json.JSONObject;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface ICrawler {
    void start(CrawlerActionListener actionListener);
    String getId();
    String getBaseUrl();
    String getRequest();
    String getCriteria();
    CrawlerProcessor getProcessor();
}
