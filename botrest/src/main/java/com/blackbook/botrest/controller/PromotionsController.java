package com.blackbook.botrest.controller;

import com.blackbook.botrest.ApiCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import view.creation_model.PromotionsCreationData;
import view.promotion.PromotionView;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/promotions")
public class PromotionsController {

    @RequestMapping(method = RequestMethod.GET)
    public List<PromotionView> getPromotions() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response
                = restTemplate.getForEntity(ApiCodes.PERSISTENCE_API + "/api/promotions", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<PromotionView> list = mapper.readValue(response.getBody(), TypeFactory.defaultInstance().constructCollectionType(List.class, PromotionView.class));

        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postPromotions(@RequestBody PromotionsCreationData promotionsCreationData) {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()); //getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<PromotionsCreationData> request = new HttpEntity<>(promotionsCreationData);
        PromotionsCreationData promotionsCreationData1 = restTemplate.postForObject(ApiCodes.PERSISTENCE_API + "/api/promotions", request, PromotionsCreationData.class);

    }
}
