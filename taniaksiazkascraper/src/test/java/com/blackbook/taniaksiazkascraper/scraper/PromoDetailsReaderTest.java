package com.blackbook.taniaksiazkascraper.scraper;

import com.blackbook.utils.view.creationmodel.BookDiscountData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PromoDetailsReaderTest {

    private final File BOOK_HTML = new File("src/test/resources/book.html");
    private final File BOOK_WITH_SUBTITLE_HTML = new File("src/test/resources/book_with_subtitle.html");

    @Test
    public void shouldReadPromotionDetails() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_HTML, "UTF-8");
        Element book = document.select(".product-container").get(0);
        PromoDetailsReader reader = new PromoDetailsReader();

        // When
        BookDiscountData discountData = reader.readDiscountDataProperties(book);

        // Then
        assertEquals("Cztery pory roku w afgańskiej wiosce", discountData.getBookData().getTitle());
        assertEquals(null, discountData.getBookData().getSubtitle());
        assertEquals("Reportaż zagraniczny", discountData.getBookData().getGenre());
        assertEquals("Anna Badkhen", discountData.getBookData().getAuthors());
        assertEquals("http://n.taniaksiazka.pl/images/medium/413/9788365506207.jpg", discountData.getBookData().getCoverUrl());
        assertEquals(19.95, discountData.getPrice());
        assertEquals("-50%", discountData.getBookDiscountDetails());
    }

    @Test
    public void shouldReadPromotionDetailsWithSubtitle() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_WITH_SUBTITLE_HTML, "UTF-8");
        Element book = document.select(".product-container").get(0);
        PromoDetailsReader reader = new PromoDetailsReader();

        // When
        BookDiscountData discountData = reader.readDiscountDataProperties(book);

        // Then
        assertEquals("Cztery pory roku w afgańskiej wiosce", discountData.getBookData().getTitle());
        assertEquals("Lato", discountData.getBookData().getSubtitle());
        assertEquals("Reportaż zagraniczny", discountData.getBookData().getGenre());
        assertEquals("Anna Badkhen", discountData.getBookData().getAuthors());
        assertEquals("http://n.taniaksiazka.pl/images/medium/413/9788365506207.jpg", discountData.getBookData().getCoverUrl());
        assertEquals(19.95, discountData.getPrice());
        assertEquals("-50%", discountData.getBookDiscountDetails());
    }
}
