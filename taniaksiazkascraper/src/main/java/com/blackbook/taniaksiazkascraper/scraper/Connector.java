package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Connector {
    Document getDocumentFromWebPage(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot connect with url: " + url);
        }
    }
}
