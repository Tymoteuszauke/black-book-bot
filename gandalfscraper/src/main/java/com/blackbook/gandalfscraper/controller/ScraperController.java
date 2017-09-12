package com.blackbook.gandalfscraper.controller;

import core.BotService;
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
@RequestMapping(value = "/api/gandalf-scraper")
public class ScraperController {

    @Autowired
    BotService botService;

    @PostMapping(produces = "application/json")
    public SimpleResponse postBookDiscounts() {
        log.info("Transaction: POST /api/gandalf-scraper");
        botService.saveResultsInDatabase();
        return SimpleResponse.builder()
                .code(HttpStatus.SC_OK)
                .message("Gandalf scraper started!!")
                .build();
    }
}
