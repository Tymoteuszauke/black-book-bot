package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;

public class Scraper {
    private Connector connector;
    private BookstoreReader bookstoreReader;
    private PromotionsPageReader promotionsPageReader;
    private BookBuilder bookBuilder;

    public Scraper(Connector connector, BookstoreReader bookstoreReader,
                   PromotionsPageReader promotionsPageReader, BookBuilder bookBuilder) {
        this.connector = connector;
        this.bookstoreReader = bookstoreReader;
        this.promotionsPageReader = promotionsPageReader;
        this.bookBuilder = bookBuilder;
    }

    public List<BookDiscountData> extractBookElements() {

        List<BookDiscountData> discountData = new LinkedList<>();
        List<String> promotionPages = bookstoreReader.getPromotionPages(connector);
        promotionPages.forEach(pageUrl -> {
            Document document = connector.getDocumentFromWebPage(pageUrl);
            Elements books = document.select(".product");
            List<Document> bookDetailsPages = new LinkedList<>();
            books.forEach(book -> bookDetailsPages.add(promotionsPageReader.readDetailsPage(connector, book)));

            bookDetailsPages.forEach(detailsPage -> discountData.add(bookBuilder.buildBookDiscountDataObject(detailsPage)));
        });

        return discountData;
    }
}