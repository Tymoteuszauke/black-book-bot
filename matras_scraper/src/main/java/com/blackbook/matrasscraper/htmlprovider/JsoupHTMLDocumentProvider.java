package com.blackbook.matrasscraper.htmlprovider;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
public class JsoupHTMLDocumentProvider implements HTMLDocumentProvider{
    @Override
    public Document provide(String url) {
        return connectToUrl(url);
    }

    private Document connectToUrl(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            //log.error("Unable to connect with URL" + url);
        }
        return document;
    }
}
