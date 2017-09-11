package com.blackbook.gandalfscraper.htmlprovider;

import org.jsoup.nodes.Document;

/**
 * @author "Patrycja Zaremba"
 */
public interface WebConnector {
    Document connect(String url);
}
