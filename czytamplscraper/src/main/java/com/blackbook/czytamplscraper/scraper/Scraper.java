package com.blackbook.czytamplscraper.scraper;

import core.CrawlerActionListener;
import core.ICrawler;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
    public void start(final CrawlerActionListener actionListener, ExecutorService executorService) {
        List<BookDiscountData> discountData = new LinkedList<>();
        List<String> promotionPages = bookstoreReader.getPromotionPages(connector);
        promotionPages.forEach(pageUrl -> {
            Document document = connector.getDocumentFromWebPage(pageUrl);
            Elements books = document.select(".product");

            List<Document> bookDetailsPages = new LinkedList<>();
            books.forEach(book -> bookDetailsPages.add(promotionsPageReader.readDetailsPage(connector, book)));

            bookDetailsPages.forEach(detailsPage -> discountData.add(bookBuilder.buildBookDiscountDataObject(detailsPage)));
        });
        actionListener.crawlerFinished(discountData);
    }

    @Override
    public int getId() {
        return BOOKSTORE_ID;
    }
}