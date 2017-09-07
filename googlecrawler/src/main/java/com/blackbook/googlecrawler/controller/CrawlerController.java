package com.blackbook.googlecrawler.controller;


import com.blackbook.googlecrawler.service.CrawlerService;
import lombok.extern.slf4j.Slf4j;
import net.SimpleResponseModel;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Shevchenko
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/google-crawler")
public class CrawlerController {

    private CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SimpleResponseModel postBookDiscounts() {
        log.info("Transaction: POST /api/google-crawler");
        crawlerService.saveResultsInDatabase();
        return new SimpleResponseModel(HttpStatus.SC_OK, "Google crawler started!");
    }
}
