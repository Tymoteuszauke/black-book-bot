package com.blackbook.crowler.core;

import org.json.JSONObject;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface CrawlerActionListener {

    void crawlerStarted(String crawlerId);
    void crawlerFinished(String crawlerId, JSONObject result);
}
