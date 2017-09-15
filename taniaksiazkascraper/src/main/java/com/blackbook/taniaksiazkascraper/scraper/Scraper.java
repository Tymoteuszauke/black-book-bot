package com.blackbook.taniaksiazkascraper.scraper;


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
    private final LastPageChecker checker;
    private final PromoDetailsReader detailsReader;
    private final CollectorsData collectorData;

    @Autowired
    public Scraper(Connector connector, LastPageChecker checker, PromoDetailsReader detailsReader) {
        this.connector = connector;
        this.checker = checker;
        this.detailsReader = detailsReader;
        this.collectorData = CollectorsData.TANIA_KSIAZKA_SCRAPER;
    }

    @Override
    public void start(Consumer<List<BookDiscountData>> consumer) {
        List<BookDiscountData> discountData = new LinkedList<>();
        int pageId = 1;
        boolean promotionsAreOnPage = true;

        while (promotionsAreOnPage) {
            String promotionPageUrl = String.format(collectorData.getBaseUrl(), pageId);
            Document document = connector.getDocumentFromWebPage(promotionPageUrl);
            Elements books = document.select(".product-container");
            books.forEach(book -> discountData.add(detailsReader.readDiscountDataProperties(connector, book)));

            if (checker.isLastPage(document)) {
                promotionsAreOnPage = false;
            } else {
                pageId++;
            }
        }
        consumer.accept(discountData);
    }

    @Override
    public int getId() {
        return collectorData.getBookStoreId();
    }
}
