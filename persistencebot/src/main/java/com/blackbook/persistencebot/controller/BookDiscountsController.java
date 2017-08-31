package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.AuthorsRepository;
import com.blackbook.persistencebot.dao.BookDiscountsRepository;

import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.service.BookDiscountParserService;
import com.blackbook.persistencebot.util.ViewMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import view.creation_model.BookDiscountData;
import view.book_discount.BookDiscountView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/book-discounts")
public class BookDiscountsController {

    @Autowired
    private BookDiscountsRepository bookDiscountsRepository;

    @Autowired
    private BookDiscountParserService bookDiscountParserService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BookDiscountView> getBookDiscounts(@RequestParam(defaultValue = "") String query,
                                                   @RequestParam(required = false) String priceFrom,
                                                   @RequestParam(required = false) String priceTo) {
        log.info("Transaction: GET /api/book-discounts");

        List<BookDiscount> bookDiscounts;

        if (!StringUtils.isEmpty(priceFrom) && !StringUtils.isEmpty(priceTo)) {
            Double from = Double.parseDouble(priceFrom);
            Double to = Double.parseDouble(priceTo);
            bookDiscounts = bookDiscountsRepository.findAllTextualSearchBetweenPrices(query, from, to);
        } else {
            bookDiscounts = bookDiscountsRepository.findAllTextualSearch(query);
        }

        return bookDiscounts
                .stream()
                .map(ViewMapperUtil::bookDiscountViewConverter)
                .collect(Collectors.toList());

    }

    @RequestMapping(method = RequestMethod.POST)
    public List<BookDiscountView> postBookDiscounts(@RequestBody List<BookDiscountData> bookDiscountData) {
        log.info("Transaction: POST /api/book-discounts");

        List<BookDiscount> bookDiscounts = bookDiscountData
                .stream()
                .map(bookDiscountParserService::parseBookDiscountData)
                .collect(Collectors.toList());

        bookDiscountsRepository.save(bookDiscounts);
        return bookDiscounts
                .stream()
                .map(ViewMapperUtil::bookDiscountViewConverter)
                .collect(Collectors.toList());
    }
}