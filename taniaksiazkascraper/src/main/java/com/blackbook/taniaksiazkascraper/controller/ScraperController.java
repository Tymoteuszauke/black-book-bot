package com.blackbook.taniaksiazkascraper.controller;

import com.blackbook.utils.core.BotService;
import com.blackbook.utils.view.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SimpleResponse postBookDiscounts() throws IOException {
        log.info("Transaction: POST /api/taniaksiazka-scraper");
        scraperService.saveResultsInDatabase();
        return SimpleResponse.builder()
                .code(HttpStatus.SC_OK)
                .message("Taniaksiazka.pl scraper started!")
                .build();
    }
}
