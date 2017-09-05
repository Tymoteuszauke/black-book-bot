package com.blackbook.czytamplscraper.controller;

import com.blackbook.czytamplscraper.scraper.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import view.bookdiscount.BookDiscountView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/czytampl-scraper")
public class ScraperController {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @RequestMapping(method = RequestMethod.POST)
    public List<BookDiscountView> postBookDiscounts() {
        log.info("Transaction: POST /api/czytampl-scraper");
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Scraper scraper = new Scraper();
        HttpEntity<Object> request = new HttpEntity<>(scraper.extractBookElements());

        return (List<BookDiscountView>) restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
    }
}