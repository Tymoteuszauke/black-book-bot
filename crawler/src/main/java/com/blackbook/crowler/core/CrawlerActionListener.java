package com.blackbook.crowler.core;

import com.mashape.unirest.http.JsonNode;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */
public interface CrawlerActionListener {

    void crawlerStarted(String crawlerId);
    void crawlerFinished(String crawlerId, JsonNode result);
}
