package com.blackbook.matrasscraper.htmlprovider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author "Patrycja Zaremba"
 */
public class JsoupHTMLDocumentProviderTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNotExistingUrl() {
        //given
        JsoupHTMLDocumentProvider documentProvider = new JsoupHTMLDocumentProvider();
        String notExistingUrl = "http://not_url";
        //when
        documentProvider.provide(notExistingUrl);
    }
}
