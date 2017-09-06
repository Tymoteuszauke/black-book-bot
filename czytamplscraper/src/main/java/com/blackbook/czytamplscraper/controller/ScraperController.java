package com.blackbook.czytamplscraper.controller;

import com.blackbook.czytamplscraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/czytampl-scraper")
public class ScraperController {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @Autowired
    private ScraperService scraperService;

    @RequestMapping(method = RequestMethod.POST)
    public void postBookDiscounts() {
        log.info("Transaction: POST /api/czytampl-scraper");
        scraperService.saveResultsInDatabase();
    }
}