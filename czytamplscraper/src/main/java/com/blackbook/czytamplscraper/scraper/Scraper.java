package com.blackbook.czytamplscraper.scraper;

import com.blackbook.utils.core.ICrawler;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class Scraper implements ICrawler {

    public static final int BOOKSTORE_ID = 2;

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

    @Override
    public void start(Consumer<List<BookDiscountData>> consumer) {
        List<BookDiscountData> discountData = new LinkedList<>();
        List<String> promotionPages = bookstoreReader.getPromotionPages(connector);
        promotionPages.forEach(pageUrl -> {
            Document document = connector.getDocumentFromWebPage(pageUrl);
            Elements books = document.select(".product");

            List<Document> bookDetailsPages = new LinkedList<>();
            books.forEach(book -> bookDetailsPages.add(promotionsPageReader.readDetailsPage(connector, book)));

            bookDetailsPages.forEach(detailsPage -> discountData.add(bookBuilder.buildBookDiscountDataObject(detailsPage)));
        });
        consumer.accept(discountData);
    }

    @Override
    public int getId() {
        return BOOKSTORE_ID;
    }
}