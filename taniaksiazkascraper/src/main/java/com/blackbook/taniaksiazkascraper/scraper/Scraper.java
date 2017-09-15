package com.blackbook.taniaksiazkascraper.scraper;


import com.blackbook.utils.core.ICrawler;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Component
public class Scraper implements ICrawler {

    public static final int BOOKSTORE_ID = 3;

    private static final String PROMOTION_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-%d";
    private Connector connector;
    private LastPageChecker checker;
    private PromoDetailsReader detailsReader;

    @Autowired
    public Scraper(Connector connector, LastPageChecker checker, PromoDetailsReader detailsReader) {
        this.connector = connector;
        this.checker = checker;
        this.detailsReader = detailsReader;
    }

    @Override
    public void start(Consumer<List<BookDiscountData>> consumer) {
        List<BookDiscountData> discountData = new LinkedList<>();
        int pageId = 1;
        boolean promotionsAreOnPage = true;

        while (promotionsAreOnPage) {
            String promotionPageUrl = String.format(PROMOTION_PAGE_URL, pageId);
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
        return BOOKSTORE_ID;
    }
}
