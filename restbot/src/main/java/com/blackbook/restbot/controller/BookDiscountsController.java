package com.blackbook.restbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import view.bookdiscount.BookDiscountView;
import view.creationmodel.BookDiscountData;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/book-discounts")
public class BookDiscountsController {

    @Value("${endpoints.persistence-api}")
    private String persistenceApiEndpoint;

    @RequestMapping(method = RequestMethod.GET)
    public Page<BookDiscountView> getBookDiscounts(@RequestParam(defaultValue = "") String query,
                                                   @RequestParam(required = false) String priceFrom,
                                                   @RequestParam(required = false) String priceTo,
                                                   Pageable pageable) throws IOException {
        log.info("Transaction: GET /api/book-discounts");
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(persistenceApiEndpoint + "/api/book-discounts");
        uriComponentsBuilder.queryParam("query", query);
        if (!StringUtils.isEmpty(priceFrom) && !StringUtils.isEmpty(priceTo)) {
            uriComponentsBuilder.queryParam("priceFrom", priceFrom);
            uriComponentsBuilder.queryParam("priceTo", priceTo);
        }

        ResponseEntity<String> response
                = restTemplate.getForEntity(uriComponentsBuilder.build().encode().toUri(), String.class);
        ObjectMapper mapper = new ObjectMapper();

        List<BookDiscountView> bookDiscounts = mapper.readValue(response.getBody(), TypeFactory.defaultInstance().constructCollectionType(List.class, BookDiscountView.class));

        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > bookDiscounts.size() ? bookDiscounts.size() : (start + pageable.getPageSize());
        return new PageImpl<>(bookDiscounts.subList(start, end), pageable, bookDiscounts.size());
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<BookDiscountView> postBookDiscounts(@RequestBody List<BookDiscountData> bookDiscountsData) {
        log.info("Transaction: POST /api/book-discounts");
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Object> request = new HttpEntity<>(bookDiscountsData);
        return (List<BookDiscountView>) restTemplate.postForObject(persistenceApiEndpoint + "/api/book-discounts", request, List.class);
    }
}
