package com.blackbook.czytampl.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class WebReader {

    Document getDocumentFromWebPage(String url){
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot connect with url: " + url);
        }
    }
}
