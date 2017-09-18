package com.blackbook.czytamplscraper.scraper;

import com.blackbook.utils.core.Collector;
import com.blackbook.utils.model.CollectorsData;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class Scraper implements Collector {

    private final Connector connector;
    private final BookstoreReader bookstoreReader;
    private final PromotionsPageReader promotionsPageReader;
    private final BookBuilder bookBuilder;
    private final CollectorsData collectorData;

    @Autowired
    public Scraper(Connector connector, BookstoreReader bookstoreReader,
                   PromotionsPageReader promotionsPageReader, BookBuilder bookBuilder) {
        this.connector = connector;
        this.bookstoreReader = bookstoreReader;
        this.promotionsPageReader = promotionsPageReader;
        this.bookBuilder = bookBuilder;
        this.collectorData = CollectorsData.CZYTAMPL_SCRAPER;
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
        return collectorData.getBookStoreId();
    }
}