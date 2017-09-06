package com.blackbook.matrasscraper.htmlprovider;

import org.jsoup.nodes.Document;

/**
 * @author "Patrycja Zaremba"
 */
public interface HTMLDocumentProvider {
    Document provide(String url);
}
