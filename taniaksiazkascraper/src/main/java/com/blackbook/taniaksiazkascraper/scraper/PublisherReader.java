package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.nodes.Document;

public class PublisherReader {

    String readBookPublisher(Document detailsPage) {
        return detailsPage.select(".with-extra-name").select("a")
                .attr("href")
                .replaceAll("-", ".")
                .replaceAll("/wydawnictwo/", "")
                .toLowerCase();
    }
}
