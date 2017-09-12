package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookstoreReaderTest {

    private final String TEST_URL = "http://czytam.pl/tania-ksiazka.html";
    private final File HTML_RESOURCE_FILE = new File("src/test/resources/no_of_pages_snippet.html");
    private Connector mockReader;

    @BeforeMethod
    private void before() throws IOException {
        mockReader = mock(Connector.class);
        when(mockReader.getDocumentFromWebPage(TEST_URL)).thenReturn(Jsoup.parse(HTML_RESOURCE_FILE, "UTF-8"));
    }

    @Test
    public void testGetPromotionPages() throws Exception {
        // Given
        BookstoreReader bookstoreReader = new BookstoreReader();
        List<String> expectedPages = Arrays.asList(
                "http://czytam.pl/tania-ksiazka,1.html",
                "http://czytam.pl/tania-ksiazka,2.html",
                "http://czytam.pl/tania-ksiazka,3.html",
                "http://czytam.pl/tania-ksiazka,4.html",
                "http://czytam.pl/tania-ksiazka,5.html",
                "http://czytam.pl/tania-ksiazka,6.html",
                "http://czytam.pl/tania-ksiazka,40.html",
                "http://czytam.pl/tania-ksiazka,41.html",
                "http://czytam.pl/tania-ksiazka,42.html"
        );

        // When
        List<String> promotionPages = bookstoreReader.getPromotionPages(mockReader);

        // Then
        assertTrue(promotionPages.containsAll(expectedPages));
    }

    @Test
    public void shouldGetNoOfPages() throws Exception {
        // Given
        BookstoreReader bookstoreReader = new BookstoreReader();

        // When
        Integer numberOfPages = bookstoreReader.getNumberOfPages(mockReader, TEST_URL);

        // Then
        assertEquals("42", String.valueOf(numberOfPages));
    }
}