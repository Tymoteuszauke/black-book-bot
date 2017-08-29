package com.blackbook.botpersistence.controller;

import com.blackbook.botpersistence.dao.BookstoresRepository;
import com.blackbook.botpersistence.dao.PromotionsRepository;
import com.blackbook.botpersistence.model.Author;
import com.blackbook.botpersistence.model.Book;
import com.blackbook.botpersistence.model.Promotion;
import com.blackbook.botpersistence.util.MappingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import view.creation_model.BookCreationData;
import view.creation_model.PromotionCreationData;
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
    private BookstoresRepository bookstoresRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<PromotionView> getPromotions() {
        log.info("Transaction: GET /api/promotions");
        return promotionsRepository.findAll().stream().map(MappingUtil::PromotionViewFromPromotion).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postPromotion(@RequestBody PromotionsCreationData promotionsCreationData) {
        log.info("Transaction: POST /api/promotions");

        List<Promotion> promotions = promotionsCreationData
                .getPromotions()
                .stream()
                .map(this::parsePromotionCreationData)
                .collect(Collectors.toList());

//        Promotion promotion = parsePromotionCreationData(promotionCreationData);

        promotionsRepository.save(promotions);
//        return String.format("items added");
    }

    private Promotion parsePromotionCreationData(PromotionCreationData promotionCreationData) {
        Promotion promotion = new Promotion();

        promotion.setPrice(promotionCreationData.getPrice());
        promotion.setPromotionDetails(promotionCreationData.getPromotionDetails());

        promotion.setBook(parseBookCreationData(promotionCreationData.getBookCreationData()));

        if (promotionCreationData.getBookstoreId() != null) {
            promotion.setBookstore(bookstoresRepository.findOne((long) promotionCreationData.getBookstoreId()));
        }
        return promotion;
    }

    private Book parseBookCreationData(BookCreationData bookCreationData) {
        Book book = new Book();
        book.setTitle(bookCreationData.getTitle());
        book.setSubtitle(bookCreationData.getSubtitle());
        book.setAuthors(bookCreationData
                .getAuthors()
                .stream()
                .map(authorCreationData -> {
                    Author author = new Author();
                    author.setName(authorCreationData.getName());
                    author.setSurname(authorCreationData.getSurname());
                    return author;
        }).collect(Collectors.toList()));
        book.setGenre(bookCreationData.getGenre());
        return book;
    }

}
