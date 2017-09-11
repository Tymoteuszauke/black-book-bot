package com.blackbook.matrasscraper.service;

import com.blackbook.matrasscraper.scraper.Scraper;
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

import java.util.List;

@Slf4j
@Service
public class ScraperService {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @Autowired
    private
    Scraper scraper;

    @Async
    public void saveResultsInDatabase() {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Object> request = new HttpEntity<>(scraper.extractBookElements());

        restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
        log.info("matras scrap results were send to database");
    }
}
