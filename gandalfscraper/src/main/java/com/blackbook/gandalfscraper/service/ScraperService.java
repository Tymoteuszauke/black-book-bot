package com.blackbook.gandalfscraper.service;

import com.blackbook.gandalfscraper.scraper.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import view.response.SimpleResponse;

@Slf4j
@Service
public class ScraperService {

    @Autowired
    private Scraper scraper;

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @Async
    public void saveResultsInDatabase() {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Object> request = new HttpEntity<>(scraper.scrapeBooks());
        restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, SimpleResponse.class);
        log.info("Gandalf scraper results were send to database");
    }
}
