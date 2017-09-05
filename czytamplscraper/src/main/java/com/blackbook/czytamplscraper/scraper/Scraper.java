package com.blackbook.czytamplscraper.scraper;

import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;

public class Scraper {

    private static final String START_PAGE = "http://czytam.pl/tania-ksiazka.html";
    private Connector connector;
    private BookstoreReader bookstoreReader;
    private PromoPageReader pageReader;

    public List<BookDiscountData> extractBookElements() {
        connector = new Connector();
        bookstoreReader = new BookstoreReader();
        pageReader = new PromoPageReader();

        List<BookDiscountData> discountData = new LinkedList<>();
        List<String> promotionPages = bookstoreReader.getPromotionPages(connector, START_PAGE);
        promotionPages.forEach(pageUrl -> discountData.addAll(pageReader.readAllDiscountsFromPage(connector, pageUrl)));

        return discountData;
    }
}