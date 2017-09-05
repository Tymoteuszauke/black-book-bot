package com.blackbook.taniaksiazkascraper.scraper;

import org.testng.annotations.Test;

public class ConnectorTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidUrl() throws Exception {
        // Given
        Connector connector = new Connector();
        String invalidUrl = "http://noSuchPage.pl/tania-ksiazka.html";

        // When
        connector.getDocumentFromWebPage(invalidUrl);
    }
}