package com.blackbook.matrasscraper.htmlprovider;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
@Component
public class JsoupHTMLDocumentProvider implements HTMLDocumentProvider {
    @Override
    public Document provide(String url) {
        return connectToUrl(url);
    }

    private Document connectToUrl(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("Unable to connect with URL " + url);
            throw new IllegalArgumentException("Unable to connect with URL " + url);
        }
    }
}
