package com.blackbook.googlecrawler.controller;



import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CrawlerScraperService;
import view.response.SimpleResponse;

/**
 * @author Sergey Shevchenko
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/google-crawler")
public class CrawlerController {

    private CrawlerScraperService crawlerScraperService;

    @Autowired
    public CrawlerController(CrawlerScraperService crawlerScraperService) {
        this.crawlerScraperService = crawlerScraperService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SimpleResponse postBookDiscounts() {
        log.info("Transaction: POST /api/google-crawler");
        crawlerScraperService.saveResultsInDatabase();
        return SimpleResponse.builder()
                .code(HttpStatus.SC_OK)
                .message("Google crawler started!")
                .build();
    }
}
