package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.creation_model.BookDiscountData;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Scraper {
    public final static int BOOKSTORE_ID = 3;
    private static final String BOOKSTORE_URL = "http://www.taniaksiazka.pl";
    private static final String PROMOTION_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-%d";
    private static final String LAST_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-253";

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
                    discountData.add(readDiscountDataProperties(book));
                });
                pageId++;
                promotionsAreOnPage = false;
            }
        }
        //discountData.forEach(System.out::println);
    }

    private static BookDiscountData readDiscountDataProperties(Element book) {
        System.out.println("Discount: " + book.select(".product-discount").text());
        System.out.println("Price: " + book.select("a").attr("data-price"));
        System.out.println("Title: " + book.select("a").attr("data-name"));
        System.out.println("Genre: " + book.select("a").attr("data-category"));
        System.out.println("Authors: " + book.select(".product-authors").text());
        System.out.println("Book details page: " + BOOKSTORE_URL + book.select("a").next().attr("href"));
        System.out.println("Cover URL: " + book.select("img").attr("src"));
        return null;
    }

    private static boolean isLastPage(Document document) {
        Elements elements = document.select(".links").select(".next");
        return elements.text().equals("");
    }
}
