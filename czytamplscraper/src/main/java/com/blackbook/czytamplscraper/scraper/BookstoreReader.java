package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BookstoreReader {
    private static final String START_PAGE = "http://czytam.pl/tania-ksiazka.html";
    private static final String promotionPageTemplate = "http://czytam.pl/tania-ksiazka,%d.html";

    List<String> getPromotionPages(Connector connector) {
        List<String> promotionPages = new ArrayList<>();
        IntStream.range(1, getNumberOfPages(connector, START_PAGE) + 1).forEach(pageId -> promotionPages.add(String.format(promotionPageTemplate, pageId)));
        return promotionPages;
    }

    Integer getNumberOfPages(Connector connector, String url) {
        Document document = connector.getDocumentFromWebPage(url);
        return Integer.valueOf(document
                .select(".pagination")
                .select(".show-for-medium-up")
                .next()
                .first()
                .text());
    }
}