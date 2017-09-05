package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import view.creationmodel.BookDiscountData;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Scraper {
    private static final String LAST_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-253";
    private static final String PROMOTION_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-%d";
    private static final PromoDetailsReader detailsReader = new PromoDetailsReader();

    public static void main(String[] args) throws IOException {

        List<BookDiscountData> discountData = new LinkedList<>();
        int pageId = 1;
        boolean promotionsAreOnPage = true;
        while (promotionsAreOnPage) {
            String promotionPageUrl = String.format(PROMOTION_PAGE_URL, pageId);
            Document document = Jsoup.connect(promotionPageUrl).get();
            if (isLastPage(document)) {
                promotionsAreOnPage = false;
            } else {
                Elements books = document.select(".product-container");
                books.forEach(book -> {
                    discountData.add(detailsReader.readDiscountDataProperties(book));
                });
                pageId++;
                promotionsAreOnPage = false;
            }
        }
        discountData.forEach(System.out::println);
    }

    private static boolean isLastPage(Document document) {
        Elements elements = document.select(".links").select(".next");
        return elements.text().equals("");
    }
}
