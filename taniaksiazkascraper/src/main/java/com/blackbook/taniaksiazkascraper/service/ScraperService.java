package com.blackbook.taniaksiazkascraper.service;

import com.blackbook.taniaksiazkascraper.scraper.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

@Slf4j
@Service
public class ScraperService {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    private RestOperations restOperations;
    private Scraper scraper;

    @Autowired
    public ScraperService(RestOperations restOperations, Scraper scraper) {
        this.restOperations = restOperations;
        this.scraper = scraper;
    }

    @Async
    public void saveResultsInDatabase() {
        HttpEntity<Object> request = new HttpEntity<>(scraper.extractBookElements());
        restOperations.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
        log.info("tania ksiazka scrap results were send to database");
    }
}
