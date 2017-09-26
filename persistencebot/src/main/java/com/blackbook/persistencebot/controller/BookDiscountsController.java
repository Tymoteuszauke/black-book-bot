package com.blackbook.persistencebot.controller;

import com.blackbook.persistencebot.dao.BookDiscountsRepository;
import com.blackbook.persistencebot.dao.BookstoresRepository;
import com.blackbook.persistencebot.dao.LogEventRepository;
import com.blackbook.persistencebot.model.BookDiscount;
import com.blackbook.persistencebot.model.Genre;
import com.blackbook.persistencebot.model.LogEventModel;
import com.blackbook.persistencebot.service.BookDiscountParserService;
import com.blackbook.persistencebot.service.GenreService;
import com.blackbook.persistencebot.util.ViewMapperUtil;
import com.blackbook.utils.model.view.BookDiscountView;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import com.blackbook.utils.model.log.LogEvent;
import com.blackbook.utils.model.response.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/book-discounts")
public class BookDiscountsController {

    private BookDiscountsRepository bookDiscountsRepository;
    private LogEventRepository logEventRepository;
    private BookstoresRepository bookstoresRepository;
    private BookDiscountParserService bookDiscountParserService;
    private GenreService genreService;

    @Autowired
    public BookDiscountsController(BookDiscountsRepository bookDiscountsRepository, BookDiscountParserService bookDiscountParserService, LogEventRepository logEventRepository, BookstoresRepository bookstoresRepository) {
        this.bookDiscountsRepository = bookDiscountsRepository;
        this.bookDiscountParserService = bookDiscountParserService;
        this.logEventRepository = logEventRepository;
        this.bookstoresRepository = bookstoresRepository;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<BookDiscountView> getBookDiscounts(@RequestParam(defaultValue = "") String query,
                                                   @RequestParam(required = false) String priceFrom,
                                                   @RequestParam(required = false) String priceTo,
                                                   @RequestParam(required = false) String genre,
                                                   Pageable pageable) {
        log.info("Transaction: GET /api/book-discounts");

        Page<BookDiscount> bookDiscounts;

        if (arePricesSpecified(priceFrom, priceTo)) {
            Double from = Double.parseDouble(priceFrom);
            Double to = Double.parseDouble(priceTo);
            bookDiscounts = bookDiscountsRepository.findAllTextualSearchBetweenPricesAndGenres(query, from, to, genre, pageable);
        } else {
            bookDiscounts = bookDiscountsRepository.findAllTextualSearch(query, pageable);
        }

        if (bookDiscounts != null) {
            return bookDiscounts
                    .map(ViewMapperUtil::bookDiscountViewConverter);
        }

        return new PageImpl<>(Collections.emptyList());
    }

    private boolean arePricesSpecified(String priceFrom, String priceTo) {
        return !StringUtils.isEmpty(priceFrom) && !StringUtils.isEmpty(priceTo);
    }

    @RequestMapping(method = RequestMethod.POST)
    public SimpleResponse postBookDiscounts(@RequestBody List<BookDiscountData> bookDiscountData) {
        try {
            log.info("Transaction: POST /api/book-discounts");
            List<Genre> genres = new ArrayList<>();
            bookDiscountData
                    .stream()
                    .distinct()
                    .map(bookDiscountParserService::parseBookDiscountData)
                    .collect(Collectors.toList());

            genreService.setGenres();

            return SimpleResponse.builder()
                    .code(HttpStatus.SC_OK)
                    .message("Books stored")
                    .build();
        } catch (Exception e) {
            return SimpleResponse.builder()
                    .code(HttpStatus.SC_CONFLICT)
                    .message("Something went wrong! Books was not saved!")
                    .build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/log")
    public SimpleResponse postLogEvent(@RequestBody LogEvent logEvent) {
        try {
            log.info("Transaction: POST /api/book-discounts/log");
            LogEventModel logEventModel = new LogEventModel();
            logEventModel.setBookStore(bookstoresRepository.findOne(logEvent.getBookStoreId()));
            logEventModel.setStartTime(Timestamp.valueOf(logEvent.getStartTime()));
            logEventModel.setFinishTime(Timestamp.valueOf(logEvent.getFinishTime()));
            logEventModel.setResult(logEvent.getResult());
            logEventRepository.save(logEventModel);
            return SimpleResponse.builder()
                    .code(HttpStatus.SC_OK)
                    .message("Log has been saved!")
                    .build();
        } catch (Exception e) {
            return SimpleResponse.builder()
                    .code(HttpStatus.SC_CONFLICT)
                    .message("Something went wrong! Log was not saved!")
                    .build();
        }
    }
}