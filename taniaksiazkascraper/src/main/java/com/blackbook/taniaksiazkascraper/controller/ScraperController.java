package com.blackbook.taniaksiazkascraper.controller;

import com.blackbook.taniaksiazkascraper.scraper.Connector;
import com.blackbook.taniaksiazkascraper.scraper.LastPageChecker;
import com.blackbook.taniaksiazkascraper.scraper.PromoDetailsReader;
import com.blackbook.taniaksiazkascraper.scraper.Scraper;
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

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/taniaksiazka-scraper")
public class ScraperController {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @RequestMapping(method = RequestMethod.POST)
    public List<BookDiscountView> postBookDiscounts() throws IOException {
        log.info("Transaction: POST /api/taniaksiazka-scraper");
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Scraper scraper = new Scraper(new Connector(), new LastPageChecker(), new PromoDetailsReader());
        HttpEntity<Object> request = new HttpEntity<>(scraper.extractBookElements());

        return (List<BookDiscountView>) restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
    }
}
