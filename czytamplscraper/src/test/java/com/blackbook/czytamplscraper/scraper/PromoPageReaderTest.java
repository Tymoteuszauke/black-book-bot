package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.creation_model.BookDiscountData;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PromoPageReaderTest {

    private final String PROMOTION_URL = "http://czytam.pl/tania-ksiazka,5.html";
    private final String BOOK_1_DETAILS = "http://czytam.pl/k,ks_295063,Przeglad-Konca-Swiata:-Deadline-Grant-Mira.html";
    private final String BOOK_2_DETAILS = "http://czytam.pl/k,ks_292286,Czartoryscy.-Opowiesc-fotograficzna-Caillot-Dubus-Barbara-Brzezinski-Marcin.html";

    private final File HTML_RESOURCE_FILE = new File("src/test/resources/promotions_page_snippet.html");
    private final File BOOK_1_DETAILS_FILE = new File("src/test/resources/book_Deadline_details_snipptet.html");
    private final File BOOK_2_DETAILS_FILE = new File("src/test/resources/book_Czartoryscy_details_snippet.html");
    private Connector mockReader;

    @BeforeMethod
    private void before() throws IOException {
        mockReader = mock(Connector.class);
        when(mockReader.getDocumentFromWebPage(PROMOTION_URL)).thenReturn(Jsoup.parse(HTML_RESOURCE_FILE, "UTF-8"));
        when(mockReader.getDocumentFromWebPage(BOOK_1_DETAILS)).thenReturn(Jsoup.parse(BOOK_1_DETAILS_FILE, "UTF-8"));
        when(mockReader.getDocumentFromWebPage(BOOK_2_DETAILS)).thenReturn(Jsoup.parse(BOOK_2_DETAILS_FILE, "UTF-8"));
    }

    @Test
    public void shouldReadAllDiscountsFromPage() throws Exception {
        // Given
        PromoPageReader pageReader = new PromoPageReader();

        // When
        List<BookDiscountData> discountData = pageReader.readAllDiscountsFromPage(mockReader, PROMOTION_URL);

        // Then
        Assert.assertEquals(2, discountData.size());
        Assert.assertEquals("Przegląd Końca Świata: Deadline", discountData.get(0).getBookData().getTitle());
        Assert.assertEquals("Czartoryscy. Opowieść fotograficzna", discountData.get(1).getBookData().getTitle());
    }
}