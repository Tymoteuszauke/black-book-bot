package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PromotionsPageReader {

    private static final String STORE_PAGE = "http://czytam.pl";

    Document readDetailsPage(Connector connector, Element book) {
        String bookDetailsUrl = STORE_PAGE + book
                .select(".image-container")
                .select("a")
                .attr("href")
                .replaceAll("\t", "")
                .replaceAll("\n", "");
        return connector.getDocumentFromWebPage(bookDetailsUrl);
    }
}