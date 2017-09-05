package com.blackbook.czytamplscraper.scraper;

import org.testng.annotations.Test;

public class ConnectorTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidUrl() throws Exception {
        // Given
        String invalidUrl = "http://noSuchPage.pl/tania-ksiazka.html";
        Connector connector = new Connector();

        // When
        connector.getDocumentFromWebPage(invalidUrl);
    }
}