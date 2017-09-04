package com.blackbook.matrasscraper.htmlprovider;

import org.jsoup.nodes.Document;

/**
 * @author "Patrycja Zaremba"
 */
public interface HTMLDocumentProvider {
    public Document provide(String url);
}
