package com.blackbook.matrasscraper.controller;

import com.blackbook.matrasscraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import view.response.SimpleResponse;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/matras-scraper")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @PostMapping(produces = "application/json")
    public SimpleResponse postBookDiscounts() {
        log.info("Transaction: POST /api/matras-scraper");
        scraperService.saveResultsInDatabase();
        return SimpleResponse.builder()
                .code(HttpStatus.SC_OK)
                .message("Matras scraper results saved in database!")
                .build();
    }
}
