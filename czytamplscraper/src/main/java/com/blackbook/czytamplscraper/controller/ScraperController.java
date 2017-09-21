package com.blackbook.czytamplscraper.controller;


import com.blackbook.utils.core.BotService;
import com.blackbook.utils.model.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/czytampl-scraper")
public class ScraperController {

    private BotService scraperService;

    @Autowired
    public ScraperController(BotService scraperService) {
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