package com.blackbook.matrasscraper.controller;

import com.blackbook.matrasscraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/matras-scraper")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @RequestMapping(method = RequestMethod.POST)
    public void postBookDiscounts() {
        log.info("Transaction: POST /api/matras-scraper");
        scraperService.saveResultsInDatabase();
    }
}
