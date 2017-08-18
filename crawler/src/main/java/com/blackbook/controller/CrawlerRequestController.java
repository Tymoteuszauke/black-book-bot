package com.blackbook.controller;


import com.blackbook.crawler.CrawlersManager;
import com.blackbook.crawler.core.ICrawlersManager;
import com.blackbook.crawler.impl.GoogleCrawler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */

@RestController
@RequestMapping(value = "/api/crawlers")
public class CrawlerRequestController {

    private final ICrawlersManager crawlersManager;

    public CrawlerRequestController() {
        crawlersManager = new CrawlersManager();
        crawlersManager.addCrawler(new GoogleCrawler());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/start")
    public void startAllCrawlers() {
        crawlersManager.startAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/start/{id}")
    public void startCrawler(@PathParam("id") String crawlerId) {
        crawlersManager.startCrawler(crawlerId);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<String> getCrawlersList()  {
       return crawlersManager.getCrawlersId();
    }

}
