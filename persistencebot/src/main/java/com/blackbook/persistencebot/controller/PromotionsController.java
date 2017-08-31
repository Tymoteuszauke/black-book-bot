package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.AuthorsRepository;
import com.blackbook.persistencebot.dao.PromotionsRepository;
import com.blackbook.persistencebot.model.Promotion;
import com.blackbook.persistencebot.service.PromotionParserService;
import com.blackbook.persistencebot.util.ViewMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import view.creation_model.PromotionsCreationData;
import view.promotion.PromotionView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/promotions")
public class PromotionsController {

    @Autowired
    private PromotionsRepository promotionsRepository;

    @Autowired
    private PromotionParserService promotionParserService;

    @Autowired
    private AuthorsRepository authorsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<PromotionView> getPromotions(@RequestParam(defaultValue = "") String query,
                                             @RequestParam(required = false) String priceFrom,
                                             @RequestParam(required = false) String priceTo) {
        log.info("Transaction: GET /api/promotions");

        List<Promotion> promotions;

        if (!StringUtils.isEmpty(priceFrom) && !StringUtils.isEmpty(priceTo)) {
            Double from = Double.parseDouble(priceFrom);
            Double to = Double.parseDouble(priceTo);
            promotions = promotionsRepository.findAllTextualSearchBetweenPrices(query, from, to);
        } else {
            promotions = promotionsRepository.findAllTextualSearch(query);
        }

        return promotions
                .stream()
                .map(ViewMapperUtil::promotionViewFromPromotion)
                .collect(Collectors.toList());

    }

    @RequestMapping(method = RequestMethod.POST)
    public void postPromotion(@RequestBody PromotionsCreationData promotionsCreationData) {
        log.info("Transaction: POST /api/promotions");

        List<Promotion> promotions = promotionsCreationData
                .getPromotions()
                .stream()
                .map(promotionParserService::parsePromotionCreationData)
                .collect(Collectors.toList());

        promotionsRepository.save(promotions);
    }
}