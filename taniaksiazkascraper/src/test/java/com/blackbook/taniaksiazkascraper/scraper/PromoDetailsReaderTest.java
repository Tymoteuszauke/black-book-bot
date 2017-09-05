package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.Test;
import view.creation_model.BookDiscountData;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.*;

public class PromoDetailsReaderTest {

    private final File BOOK_HTML = new File("src\\test\\resources\\book.html");

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
        assertEquals("Reportaż zagraniczny", discountData.getBookData().getGenre());
        assertEquals("Anna Badkhen", discountData.getBookData().getAuthors());
        assertEquals("http://n.taniaksiazka.pl/images/medium/413/9788365506207.jpg", discountData.getBookData().getCoverUrl());
        assertEquals(19.95, discountData.getPrice());
        assertEquals("-50%", discountData.getBookDiscountDetails());
    }
}
