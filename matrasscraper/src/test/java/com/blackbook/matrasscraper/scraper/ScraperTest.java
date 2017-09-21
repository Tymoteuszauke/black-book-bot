package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import com.blackbook.matrasscraper.htmlprovider.JsoupHTMLDocumentProvider;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @author "Patrycja Zaremba"
 */
public class ScraperTest {

    @Test
    public void shouldScrapBooksFromGivenHTML() throws IOException {
        //given
        int booksOnPage = 2;
        File mainPage = new File("src/test/resources/matras.html");
        File bookPage = new File("src/test/resources/matras_book.html");
        File bookPageNoSubtitle = new File("src/test/resources/matras_book_no_subtitle.html");
        File paginationPage = new File("src/test/resources/matras_pagination.html");
        HTMLDocumentProvider htmlDocumentProvider = mock(JsoupHTMLDocumentProvider.class);
        when(htmlDocumentProvider.provide(anyString()))
                .thenReturn(Jsoup.parse(paginationPage, "UTF-8"),
                        Jsoup.parse(mainPage, "UTF-8"),
                        Jsoup.parse(bookPageNoSubtitle, "UTF-8"),
                        Jsoup.parse(bookPage, "UTF-8"));
        Scraper scraper = new Scraper(htmlDocumentProvider);
        scraper.lastPageNo = 1;
        //when
        scraper.start(booksData -> {

            //then
            assertEquals(booksOnPage, booksData.size());
        });
    }
}
