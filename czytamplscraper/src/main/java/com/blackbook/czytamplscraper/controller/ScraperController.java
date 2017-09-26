package com.blackbook.czytamplscraper.controller;


import com.blackbook.utils.core.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> postBookDiscounts() {
        log.info("Transaction: POST /api/czytampl-scraper");
        scraperService.saveResultsInDatabase();
        return ResponseEntity.ok("Czytam.pl scraper started!");
    }
}