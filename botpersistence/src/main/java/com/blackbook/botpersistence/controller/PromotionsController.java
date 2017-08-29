package com.blackbook.botpersistence.controller;

import com.blackbook.botpersistence.dao.PromotionsRepository;
import com.blackbook.botpersistence.model.Promotion;
import com.blackbook.botpersistence.util.MappingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import view.promotion.PromotionView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/promotions")
public class PromotionsController {

    @Autowired
    private PromotionsRepository promotionsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<PromotionView> getPromotions() {
        log.info("Transaction: GET /api/promotions");
        return promotionsRepository.findAll().stream().map(MappingUtil::PromotionViewFromPromotion).collect(Collectors.toList());
    }

}
