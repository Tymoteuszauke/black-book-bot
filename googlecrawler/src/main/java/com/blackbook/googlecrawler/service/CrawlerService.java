package com.blackbook.googlecrawler.service;

import com.blackbook.googlecrawler.impl.GoogleCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author Siarhei Shauchenka
 */
@Slf4j
@Service
public class CrawlerService {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @Async
    public void saveResultsInDatabase() {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        GoogleCrawler googleCrawler = new GoogleCrawler(booksData -> {
            HttpEntity<Object> request = new HttpEntity<>(booksData);
            restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
            log.info("google crawler results were send to database");
        }, Executors.newCachedThreadPool());
        googleCrawler.start();
    }
}
