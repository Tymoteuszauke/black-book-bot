package com.blackbook.botpersistence.controller;

import com.blackbook.botpersistence.dao.AuthorsRepository;
import com.blackbook.botpersistence.dao.PromotionsRepository;
import com.blackbook.botpersistence.model.Author;
import com.blackbook.botpersistence.model.Promotion;
import com.blackbook.botpersistence.service.PromotionParserService;
import com.blackbook.botpersistence.util.ViewMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .map(ViewMapUtil::PromotionViewFromPromotion)
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