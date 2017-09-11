package com.blackbook.gandalfscraper.scraper;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * @author "Patrycja Zaremba"
 */
@Component
public class LastPageChecker {
    public int extractLastPage(Document mainPageDocument) {
        return Integer.parseInt(mainPageDocument.select(".paging_number_link")
            .last()
            .text());
    }
}
