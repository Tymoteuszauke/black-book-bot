package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Connector {

    Document getDocumentFromWebPage(String url){
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot connect with url: " + url);
        }
    }
}
