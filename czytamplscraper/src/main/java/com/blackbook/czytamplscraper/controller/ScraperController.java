package com.blackbook.czytamplscraper.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CrawlerScraperService;
import view.response.SimpleResponse;

@Slf4j
@RestController
@RequestMapping(value = "/api/czytampl-scraper")
public class ScraperController {

    private CrawlerScraperService scraperService;

    @Autowired
    public ScraperController(CrawlerScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public SimpleResponse postBookDiscounts() {
        log.info("Transaction: POST /api/czytampl-scraper");
        scraperService.saveResultsInDatabase();
        return SimpleResponse.builder()
                .code(HttpStatus.SC_OK)
                .message("Czytam.pl scraper started!")
                .build();
    }
}