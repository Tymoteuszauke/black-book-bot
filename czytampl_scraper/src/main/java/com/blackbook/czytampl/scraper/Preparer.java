package com.blackbook.czytampl.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

// todo: rename
public class Preparer {

    // todo: externalize?
    private final String promotionPageTemplate = "http://czytam.pl/tania-ksiazka,%d.html";

    List<String> getPromotionPages(WebReader webReader, String url) throws IOException {
        List<String> promotionPages = new ArrayList<>();
        IntStream.range(1, getNumberOfPages(webReader, url) + 1).forEach(pageId -> promotionPages.add(String.format(promotionPageTemplate, pageId)));
        return promotionPages;
    }

    Integer getNumberOfPages(WebReader webReader, String url) throws IOException {
        Document document = webReader.getDocumentFromWebPage(url);
        return Integer.valueOf(document
                .select(".pagination")
                .select(".show-for-medium-up")
                .next()
                .first()
                .text());
    }
}
