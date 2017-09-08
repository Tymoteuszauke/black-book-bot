package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.service.BookDiscountParserService;
import com.blackbook.persistencebot.util.ViewMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import view.bookdiscount.BookDiscountView;
import view.creationmodel.BookDiscountData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/book-discounts")
public class BookDiscountsController {

    private BookDiscountsRepository bookDiscountsRepository;

    private BookDiscountParserService bookDiscountParserService;

    @Autowired
    public BookDiscountsController(BookDiscountsRepository bookDiscountsRepository, BookDiscountParserService bookDiscountParserService) {
        this.bookDiscountsRepository = bookDiscountsRepository;
        this.bookDiscountParserService = bookDiscountParserService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<BookDiscountView> getBookDiscounts(@RequestParam(defaultValue = "") String query,
                                                   @RequestParam(required = false) String priceFrom,
                                                   @RequestParam(required = false) String priceTo,
                                                   Pageable pageable) {
        log.info("Transaction: GET /api/book-discounts");

        Page<BookDiscount> bookDiscounts;

        if (arePricesSpecified(priceFrom, priceTo)) {
            Double from = Double.parseDouble(priceFrom);
            Double to = Double.parseDouble(priceTo);
            bookDiscounts = bookDiscountsRepository.findAllTextualSearchBetweenPrices(query, from, to, pageable);
        } else {
            bookDiscounts = bookDiscountsRepository.findAllTextualSearch(query, pageable);
        }

        if (bookDiscounts != null) {
            return bookDiscounts
                    .map(ViewMapperUtil::bookDiscountViewConverter);
        }

        return new PageImpl<>(Collections.EMPTY_LIST);
    }

    private boolean arePricesSpecified(String priceFrom, String priceTo) {
        return !StringUtils.isEmpty(priceFrom) && !StringUtils.isEmpty(priceTo);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<BookDiscountView> postBookDiscounts(@RequestBody List<BookDiscountData> bookDiscountData) {
        log.info("Transaction: POST /api/book-discounts");

        List<BookDiscount> bookDiscounts = bookDiscountData
                .stream()
                .map(bookDiscountParserService::parseBookDiscountData)
                .distinct()
                .collect(Collectors.toList());

//        bookDiscountsRepository.save(bookDiscounts);
        return bookDiscounts
                .stream()
                .map(ViewMapperUtil::bookDiscountViewConverter)
                .collect(Collectors.toList());
    }
}