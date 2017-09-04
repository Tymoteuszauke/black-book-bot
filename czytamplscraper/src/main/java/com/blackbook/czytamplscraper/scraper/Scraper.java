package com.blackbook.czytamplscraper.scraper;

import view.creation_model.BookDiscountData;

import java.util.LinkedList;
import java.util.List;

public class Scraper {

    private static final String START_PAGE = "http://czytam.pl/tania-ksiazka.html";

    public List<BookDiscountData> extractBookElements() {
        Connector connector = new Connector();
        BookstoreReader bookstoreReader = new BookstoreReader();
        PromoPageReader pageReader = new PromoPageReader();

        List<BookDiscountData> discountData = new LinkedList<>();
        List<String> promotionPages = bookstoreReader.getPromotionPages(connector, START_PAGE);
        promotionPages.forEach(pageUrl -> discountData.addAll(pageReader.readAllDiscountsFromPage(connector, pageUrl)));

        return discountData;
    }
}