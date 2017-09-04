package com.blackbook.czytamplscraper.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

@Slf4j
class WebReader {

    Document getDocumentFromWebPage(String url){
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
                throw new IllegalArgumentException("Cannot connect with url: " + url);
        }
    }
}
