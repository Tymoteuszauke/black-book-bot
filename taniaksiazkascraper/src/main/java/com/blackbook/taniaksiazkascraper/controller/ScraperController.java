package com.blackbook.taniaksiazkascraper.controller;

import com.blackbook.taniaksiazkascraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import view.response.SimpleResponse;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/api/taniaksiazka-scraper")
public class ScraperController {

    private ScraperService scraperService;

    @Autowired
    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public SimpleResponse postBookDiscounts() throws IOException {
        log.info("Transaction: POST /api/taniaksiazka-scraper");
        scraperService.saveResultsInDatabase();
        return new SimpleResponse(HttpStatus.SC_OK, "Taniaksiazka.pl scraper results saved in database!");
    }
}
