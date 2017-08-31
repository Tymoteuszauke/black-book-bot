package com.blackbook.botrest.controller;

import com.blackbook.botrest.ApiCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import view.creation_model.PromotionsCreationData;
import view.promotion.PromotionView;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/promotions")
public class PromotionsController {

    @RequestMapping(method = RequestMethod.GET)
    public List<PromotionView> getPromotions(@RequestParam(defaultValue = "") String query,
                                             @RequestParam(required = false) String priceFrom,
                                             @RequestParam(required = false) String priceTo) throws IOException {
        log.info("Transaction: GET /api/promotions");
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(ApiCodes.PERSISTENCE_API + "/api/promotions");
        uriComponentsBuilder.queryParam("query", query);
        if (!StringUtils.isEmpty(priceFrom) && !StringUtils.isEmpty(priceTo)) {
            uriComponentsBuilder.queryParam("priceFrom", priceFrom);
            uriComponentsBuilder.queryParam("priceTo", priceTo);
        }

        ResponseEntity<String> response
                = restTemplate.getForEntity(uriComponentsBuilder.build().encode().toUri(), String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<PromotionView> list = mapper.readValue(response.getBody(), TypeFactory.defaultInstance().constructCollectionType(List.class, PromotionView.class));

        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postPromotions(@RequestBody PromotionsCreationData promotionsCreationData) {
        log.info("Transaction: POST /api/promotions");
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()); //getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<PromotionsCreationData> request = new HttpEntity<>(promotionsCreationData);
        PromotionsCreationData promotionsCreationData1 = restTemplate.postForObject(ApiCodes.PERSISTENCE_API + "/api/promotions", request, PromotionsCreationData.class);
    }
}
