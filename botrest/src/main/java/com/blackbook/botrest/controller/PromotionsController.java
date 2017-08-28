package com.blackbook.botrest.controller;

import com.blackbook.botrest.model.view.promotion.PromotionView;
import com.blackbook.botrest.dao.PromotionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/promotions")
public class PromotionsController {

    @Autowired
    private PromotionsRepository promotionsRepository;

    public List<PromotionView> getPromotions() {

        return promotionsRepository
                .findAll()
                .stream()
                .map(PromotionView::fromPromotion)
                .collect(Collectors.toList());
    }
}
