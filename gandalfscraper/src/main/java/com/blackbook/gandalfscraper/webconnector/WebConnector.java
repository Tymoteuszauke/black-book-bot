package com.blackbook.gandalfscraper.webconnector;

import org.jsoup.nodes.Document;

/**
 * @author "Patrycja Zaremba"
 */
public interface WebConnector {
    Document connect(String url);
}
