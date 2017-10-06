package com.blackbook.taniaksiazkascraper.scraper;

import com.blackbook.utils.model.creationmodel.BookDiscountData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class PromoDetailsReaderTest {
    private final File BOOK_HTML = new File("src/test/resources/book.html");
    private final File DETAILS_HTML = new File("src/test/resources/book_details.html");
    private final File BOOK_WITHOUT_SUBTITLE_HTML = new File("src/test/resources/book_without_subtitle.html");
    private final String URL = "https://www.taniaksiazka.pl/cztery-pory-roku-w-afganskiej-wiosce-anna-badkhen-p-792671.html";

    @Test
    public void shouldReadPromotionDetails() throws IOException {
        // Given
        Connector mockConnector = mock(Connector.class);
        when(mockConnector.getDocumentFromWebPage(any())).thenReturn(Jsoup.parse(DETAILS_HTML, "UTF-8"));

        Document bookDoc = Jsoup.parse(BOOK_HTML, "UTF-8");
        Element book = bookDoc.select(".product-container").get(0);
        PromoDetailsReader reader = new PromoDetailsReader();

        // When
        BookDiscountData discountData = reader.readDiscountDataProperties(mockConnector, book);

        // Then
        assertEquals(discountData.getBookData().getTitle(), "Coldplay");
        assertEquals(discountData.getBookData().getSubtitle(), "Życie w trasie");
        assertEquals(discountData.getBookData().getPublisher(), "Sine Qua Non");
        assertEquals(discountData.getBookData().getGenre(), "Muzyka rozrywkowa");
        assertEquals(discountData.getBookData().getAuthors(), "Matt McGinn");
        assertEquals(discountData.getBookDiscountDetails(), "-50%");
        assertEquals(discountData.getPrice(), 19.95);
        assertEquals(discountData.getBookData().getCoverUrl(), "https://www.taniaksiazka.pl/images/large/EA7/978837924214633.jpg");
    }

    @Test
    public void shouldReadPromotionDetailsWithoutSubtitle() throws IOException {
        // Given
        Connector mockConnector = mock(Connector.class);
        when(mockConnector.getDocumentFromWebPage(URL)).thenReturn(Jsoup.parse(BOOK_WITHOUT_SUBTITLE_HTML, "UTF-8"));

        Document bookDoc = Jsoup.parse(BOOK_HTML, "UTF-8");
        Element book = bookDoc.select(".product-container").get(0);
        PromoDetailsReader reader = new PromoDetailsReader();

        // When
        BookDiscountData discountData = reader.readDiscountDataProperties(mockConnector, book);

        // Then
        assertEquals(discountData.getBookData().getTitle(), "Coldplay");
        assertEquals(discountData.getBookData().getSubtitle(), null);
        assertEquals(discountData.getBookData().getPublisher(), "Sine Qua Non");
        assertEquals(discountData.getBookData().getGenre(), "Muzyka rozrywkowa");
        assertEquals(discountData.getBookData().getAuthors(), "Matt McGinn");
        assertEquals(discountData.getBookDiscountDetails(), "-50%");
        assertEquals(discountData.getPrice(), 19.95);
        assertEquals(discountData.getBookData().getCoverUrl(), "https://www.taniaksiazka.pl/images/large/EA7/978837924214633.jpg");
    }
}
