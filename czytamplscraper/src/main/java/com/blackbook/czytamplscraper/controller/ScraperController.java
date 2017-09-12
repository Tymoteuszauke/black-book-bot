package com.blackbook.czytamplscraper.controller;

import com.blackbook.czytamplscraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import view.response.SimpleResponse;

@Slf4j
@RestController
@RequestMapping(value = "/api/czytampl-scraper")
public class ScraperController {

    private ScraperService scraperService;

    @Autowired
    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public SimpleResponse postBookDiscounts() {
        log.info("Transaction: POST /api/czytampl-scraper");
        scraperService.saveResultsInDatabase();
        return new SimpleResponse(HttpStatus.SC_OK, "Czytam.pl scraper results saved in database!");
    }
}