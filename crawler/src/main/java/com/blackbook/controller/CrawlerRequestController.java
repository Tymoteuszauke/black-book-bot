package com.blackbook.controller;


import com.blackbook.crowler.CrawlersManager;
import com.blackbook.crowler.core.CrawlerActionListener;
import com.blackbook.crowler.core.ICrawler;
import com.blackbook.crowler.core.ICrawlersManager;
import com.blackbook.crowler.impl.ISBNdbCrawler;
import com.mashape.unirest.http.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Sergey Shevchenko
 * @since 16.08.2017
 */

@RestController
@RequestMapping(value = "/api/crawlers")
public class CrawlerRequestController {

    ICrawlersManager crawlersManager;

    public CrawlerRequestController() {
        crawlersManager = new CrawlersManager();
        crawlersManager.addCrawler(new ISBNdbCrawler("E466PBHL"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/start")
    public void startAllCrawlers() {
        crawlersManager.startAll(new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {

            }

            @Override
            public void crawlerFinished(String crawlerId, JsonNode result) {
                System.out.println(crawlerId + " " + result.getObject().toString());
            }
        });
    }

    @RequestMapping(method = RequestMethod.POST, value = "/start", consumes = "text/html")
    public void startCrawler(@RequestBody String crawlerId) {
        crawlersManager.startCrawler(crawlerId, new CrawlerActionListener() {
            @Override
            public void crawlerStarted(String crawlerId) {

            }

            @Override
            public void crawlerFinished(String crawlerId, JsonNode result) {
                System.out.println(crawlerId + " " + result.getObject().toString());
            }
        });
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<String> getCrawlersList()  {
       return crawlersManager.getCrawlersId();
    }

}
