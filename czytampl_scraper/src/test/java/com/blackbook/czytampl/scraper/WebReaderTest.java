package com.blackbook.czytampl.scraper;

import org.testng.annotations.Test;

public class WebReaderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidUrl() throws Exception {
        // Given
        String invalidUrl = "http://noSuchPage.pl/tania-ksiazka.html";
        WebReader webReader = new WebReader();

        // When
        webReader.getDocumentFromWebPage(invalidUrl);
    }
}