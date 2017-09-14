package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.creationmodel.BookDiscountData;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ScraperTest {

    private BookDiscountData discountData;
    private String testPromoPage = "http://fakeUrl.com";
    private Connector connector;
    private BookstoreReader bookstoreReader;
    private BookBuilder bookBuilder;
    private PromotionsPageReader pageReader;
    private final File HTML_RESOURCE_FILE = new File("src/test/resources/promotions_page.html");
    private Document pageDoc;
    private Document bookDoc = new Document("Book");
    private Element book;

    @BeforeMethod
    public void before() throws IOException {
        pageDoc = Jsoup.parse(HTML_RESOURCE_FILE, "UTF-8");
        book = pageDoc.select(".product").get(0);

        discountData = BookDiscountData.builder()
                .price(25.00)
                .bookDiscountDetails("-25%")
                .bookstoreId(3)
                .build();

        connector = mock(Connector.class);
        when(connector.getDocumentFromWebPage(testPromoPage)).thenReturn(pageDoc);

        bookBuilder = mock(BookBuilder.class);
        when(bookBuilder.buildBookDiscountDataObject(bookDoc)).thenReturn(discountData);

        bookstoreReader = mock(BookstoreReader.class);
        when(bookstoreReader.getPromotionPages(connector)).thenReturn(Arrays.asList(testPromoPage));

        pageReader = mock(PromotionsPageReader.class);
        when(pageReader.readDetailsPage(connector, book)).thenReturn(bookDoc);
    }


    @Test
    public void shouldExtractBookElements() throws Exception {
        // Given
        Scraper scraper = new Scraper(connector, bookstoreReader, pageReader, bookBuilder);

        // When
        scraper.start(discountData -> {
            // Then
            assertEquals(discountData.size(), 1);
            assertEquals(discountData.get(0).getPrice(), 25.00);
            assertEquals(discountData.get(0).getBookDiscountDetails(), "-25%");
        });
    }

}