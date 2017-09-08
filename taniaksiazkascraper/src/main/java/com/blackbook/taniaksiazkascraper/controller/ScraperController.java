package com.blackbook.taniaksiazkascraper.controller;

import com.blackbook.taniaksiazkascraper.service.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/api/taniaksiazka-scraper")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @RequestMapping(method = RequestMethod.POST)
    public void postBookDiscounts() throws IOException {
        log.info("Transaction: POST /api/taniaksiazka-scraper");
        scraperService.saveResultsInDatabase();
    }
}
