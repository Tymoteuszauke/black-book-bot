package com.blackbook.matrasscraper.htmlprovider;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * @author "Patrycja Zaremba"
 */
public interface HTMLDocumentProvider {
    public Document provide(String url);
}
