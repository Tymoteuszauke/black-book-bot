package com.blackbook.taniaksiazkascraper.controller;

import com.blackbook.utils.core.BotService;
import com.blackbook.utils.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/api/taniaksiazka-scraper")
public class ScraperController {

    private BotService scraperService;

    @Autowired
    public ScraperController(BotService scraperService) {
        this.scraperService = scraperService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SimpleResponse<String>> postBookDiscounts() throws IOException {
        log.info("Transaction: POST /api/taniaksiazka-scraper");
        scraperService.saveResultsInDatabase();
        return ResponseEntity.ok(new SimpleResponse<>("Taniaksiazka.pl scraper started!"));
    }
}
