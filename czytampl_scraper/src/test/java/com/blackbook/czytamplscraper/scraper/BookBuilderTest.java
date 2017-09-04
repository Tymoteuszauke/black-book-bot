package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import view.creation_model.BookDiscountData;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class BookBuilderTest {

    private final String TEST_URL = "http://czytam.pl/k,ks_599195,Drakulcio-ma-klopoty-Straszliwa-historia-w-obrazkach-Pinkwart-Magdalena-Pinkwart-Sergiusz.html";
    private final File BOOK_HTML_FILE = new File("src/test/resources/book.html");
    private final File BOOK_DETAILS_PAGE_HTML_FILE = new File("src/test/resources/books_details_page_snippet.html");
    private WebReader mockReader;

    @BeforeTest
    public void beforeTest() throws IOException {
        mockReader = mock(WebReader.class);
        when(mockReader.getDocumentFromWebPage(TEST_URL)).thenReturn(Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8"));
    }

    @Test
    public void shouldBuildProperBookDiscountDataObject() throws Exception {
        // Given
        Document parse = Jsoup.parse(BOOK_HTML_FILE, "UTF-8");
        Element element = parse.body();
        BookBuilder bookBuilder = new BookBuilder(mockReader, element);

        // When
        BookDiscountData bookDiscountData = bookBuilder.buildBookDiscountDataObject();

        // Then
        assertEquals(6.56, bookDiscountData.getPrice());
        assertEquals(bookDiscountData.getBookData().getTitle(), "Drakulcio ma kłopoty Straszliwa historia w obrazkach");
        assertEquals(bookDiscountData.getBookData().getSubtitle(), "Straszliwa historia w obrazkach");
        assertEquals(bookDiscountData.getBookData().getAuthors(), "Pinkwart Magdalena, Pinkwart Sergiusz");
        assertEquals(bookDiscountData.getBookDiscountDetails(), "-59%");
        assertEquals(bookDiscountData.getBookData().getGenre(), "Unknown");
        assertEquals(bookDiscountData.getBookData().getBookPageUrl(), "http://czytam.pl/k,ks_599195,Drakulcio-ma-klopoty-Straszliwa-historia-w-obrazkach-Pinkwart-Magdalena-Pinkwart-Sergiusz.html");
        assertEquals(bookDiscountData.getBookData().getCoverUrl(), "http://webimage.pl/pics/073/5/822173.jpg");
    }
}