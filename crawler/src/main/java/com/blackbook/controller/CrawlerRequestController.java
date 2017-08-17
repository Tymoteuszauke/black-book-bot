package com.blackbook.controller;


import com.blackbook.crowler.CrawlersManager;
import com.blackbook.crowler.core.ICrawlersManager;
import com.blackbook.crowler.impl.GoogleCrawler;
import com.blackbook.processor.impl.GoogleProcessor;
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
        crawlersManager.addCrawler(new GoogleCrawler(new GoogleProcessor()));
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
