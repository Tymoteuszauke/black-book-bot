package com.blackbook.googlecrawler.controller;

import com.blackbook.utils.core.BotService;
import com.blackbook.utils.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private BotService crawlerScraperService;

    @Autowired
    public CrawlerController(BotService crawlerScraperService) {
        this.crawlerScraperService = crawlerScraperService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SimpleResponse<String>> postBookDiscounts() {
        log.info("Transaction: POST /api/google-crawler");
        crawlerScraperService.saveResultsInDatabase();
        return ResponseEntity.ok(new SimpleResponse<>("Google crawler started!"));
    }
}
