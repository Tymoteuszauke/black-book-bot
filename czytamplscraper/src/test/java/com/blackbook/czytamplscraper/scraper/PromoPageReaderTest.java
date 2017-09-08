package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class PromoPageReaderTest {
    private String detailsUrl = "http://czytam.pl/k,ks_569700,Poppy-Pym-i-klatwa-Faraona-Wood-Laura.html";
    private final File HTML_RESOURCE_FILE = new File("src/test/resources/promotions_page.html");
    private Connector mockReader;
    private Document testDock = new Document("AAA");

    @BeforeMethod
    private void before() throws IOException {
        mockReader = mock(Connector.class);
        when(mockReader.getDocumentFromWebPage(detailsUrl)).thenReturn(testDock);
    }

    @Test
    public void shouldGetDocumentFromBook() throws IOException {
        // Given
        PromotionsPageReader pageReader = new PromotionsPageReader();

        // When
        Document document = pageReader.readDetailsPage(mockReader, Jsoup.parse(HTML_RESOURCE_FILE, "UTF-8"));

        // Then
        assertEquals(document.baseUri(), "AAA");
    }
}