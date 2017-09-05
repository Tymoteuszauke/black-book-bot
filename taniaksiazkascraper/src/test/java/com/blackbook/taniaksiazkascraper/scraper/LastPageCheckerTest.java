package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LastPageCheckerTest {

    private final String LAST_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-253";
    private final String REGULAR_PAGE_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-200";
    private final File LAST_PAGE_HTML_FILE = new File("src/test/resources/last_page.html");
    private final File REGULAR_PAGE_HTML_FILE = new File("src/test/resources/page_with_next_pages.html");
    private Connector mockConnector;
    private LastPageChecker checker;

    @BeforeTest
    public void beforeTest() throws IOException {
        checker = new LastPageChecker();
        mockConnector = mock(Connector.class);
        when(mockConnector.getDocumentFromWebPage(LAST_PAGE_URL)).thenReturn(Jsoup.parse(LAST_PAGE_HTML_FILE, "UTF-8"));
        when(mockConnector.getDocumentFromWebPage(REGULAR_PAGE_URL)).thenReturn(Jsoup.parse(REGULAR_PAGE_HTML_FILE, "UTF-8"));
    }

    @Test
    public void shouldReturnTrueForLastPage() {
        // Given
        Document document = mockConnector.getDocumentFromWebPage(LAST_PAGE_URL);

        // When
        boolean isLast = checker.isLastPage(document);

        // Then
        assertTrue(isLast);
    }

    @Test
    public void shouldReturnFalseForRegularPage() {
        // Given
        Document document = mockConnector.getDocumentFromWebPage(REGULAR_PAGE_URL);

        // When
        boolean isLast = checker.isLastPage(document);

        // Then
        assertFalse(isLast);
    }
}