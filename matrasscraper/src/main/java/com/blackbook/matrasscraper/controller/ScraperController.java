package com.blackbook.matrasscraper.controller;

import com.blackbook.utils.core.BotService;
import com.blackbook.utils.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/matras-scraper")
public class ScraperController {

    private final BotService scraperService;

    @Autowired
    public ScraperController(BotService scraperService) {
        this.scraperService = scraperService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SimpleResponse<String>> postBookDiscounts() {
        log.info("Transaction: POST /api/matras-scraper");
        scraperService.saveResultsInDatabase();
        return ResponseEntity.ok(new SimpleResponse<>("Matras scraper started!"));
    }
}
