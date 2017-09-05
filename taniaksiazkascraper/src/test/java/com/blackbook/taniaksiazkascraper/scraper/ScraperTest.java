package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import view.creationmodel.BookDiscountData;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScraperTest {

    private Scraper scraper;
    private Connector mockConnector;
    private LastPageChecker mockChecker;
    private Document document;
    private final String TEST_URL = "http://www.taniaksiazka.pl/tanie-ksiazki/page-1";
    private final File PROMOTIONS_HTML_FILE = new File("src/test/resources/promotions_page.html");

    @BeforeTest
    public void before() throws IOException {
        document = Jsoup.parse(PROMOTIONS_HTML_FILE, "UTF-8");

        mockConnector = mock(Connector.class);
        when(mockConnector.getDocumentFromWebPage(TEST_URL)).thenReturn(document);

        mockChecker = mock(LastPageChecker.class);
        when(mockChecker.isLastPage(document)).thenReturn(true);

        scraper = new Scraper(mockConnector, mockChecker, new PromoDetailsReader());
    }

    @Test
    public void shouldReturnAllDiscountDataObjects() throws IOException {
        // When
        List<BookDiscountData> bookDiscountData = scraper.extractBookElements();

        // Then
        Assert.assertEquals(28, bookDiscountData.size());
    }
}