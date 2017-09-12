package com.blackbook.czytamplscraper.service;

import com.blackbook.czytamplscraper.scraper.Scraper;
import lombok.extern.slf4j.Slf4j;
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

    private Scraper scraper;
    private RestOperations restOperations;

    public ScraperService(Scraper scraper, RestOperations restOperations) {
        this.scraper = scraper;
        this.restOperations = restOperations;
    }

    @Async
    public void saveResultsInDatabase() {
        HttpEntity<Object> request = new HttpEntity<>(scraper.extractBookElements());
        restOperations.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
        log.info("czytam.pl scrap results were send to database");
    }
}
