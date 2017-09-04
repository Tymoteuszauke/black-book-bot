package com.blackbook.czytamplscraper.scraper;

import view.creation_model.BookDiscountData;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Scraper {

    private static final String START_PAGE = "http://czytam.pl/tania-ksiazka.html";

    public static void main(String[] args) throws IOException {
        WebReader reader = new WebReader();
        Preparer preparer = new Preparer();
        PromoPageReader pageReader = new PromoPageReader();

        List<BookDiscountData> discountData = new LinkedList<>();
        List<String> promotionPages = preparer.getPromotionPages(reader, START_PAGE);
        promotionPages.forEach(pageUrl -> discountData.addAll(pageReader.readAllDiscountsFromPage(reader, pageUrl)));

        discountData.forEach(System.out::println);
    }
}