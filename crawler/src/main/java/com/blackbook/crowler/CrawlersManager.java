package com.blackbook.crowler;

import com.blackbook.crowler.core.CrawlerActionListener;
import com.blackbook.crowler.core.ICrawler;
import com.blackbook.crowler.core.ICrawlersManager;
import org.json.JSONObject;

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

    public CrawlersManager() {
        this.crawlers = new HashMap<>();
    }

    @Override
    public void startCrawler(String crawlerId) {
        getCrawlerById(crawlerId).start(new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {

            }

            @Override
            public void crawlerFinished(String crawlerId, JSONObject result) {

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
        crawlers.forEach((key, crawler) ->  crawler.start(new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {

            }

            @Override
            public void crawlerFinished(String crawlerId, JSONObject result) {

            }
        }));
    }
}
