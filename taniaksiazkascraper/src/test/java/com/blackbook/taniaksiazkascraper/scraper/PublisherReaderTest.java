package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;


public class PublisherReaderTest {

    private final File BOOK_HTML = new File("src/test/resources/book_details.html");
    private PublisherReader publisherReader;

    @BeforeMethod
    public void before() {
        publisherReader = new PublisherReader();
    }

    @Test
    public void shouldReadBookPublisher() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_HTML, "UTF-8");

        // When
        String publisher = publisherReader.readBookPublisher(document);

        // Then
        assertEquals("w.a.b", publisher);
    }
}