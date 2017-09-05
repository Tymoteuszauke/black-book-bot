package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class LastPageChecker {
    boolean isLastPage(Document document) {
        Elements elements = document.select(".links").select(".next");
        return elements.text().equals("");
    }
}
