package com.blackbook.gandalfscraper.scraper;

import com.blackbook.gandalfscraper.webconnector.JsoupWebConnector;
import com.blackbook.gandalfscraper.webconnector.WebConnector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
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
    WebConnector webConnector;
    LastPageChecker lastPageChecker;

    @Before
    public void initMocks() throws IOException {
        int lastPageNo = 1;
        File mainPage = new File("src/test/resources/main_page.html");
        File bookPage = new File("src/test/resources/book_page.html");
        File bookPageNoSubtitleNoGenre = new File("src/test/resources/book_page_no_subtitle_no_genre.html");
        File pagingPage = new File("src/test/resources/paging.html");
        webConnector = mock(JsoupWebConnector.class);
        when(webConnector.connect(anyString()))
                .thenReturn(Jsoup.parse(pagingPage, "UTF-8"),
                        Jsoup.parse(mainPage, "UTF-8"),
                        Jsoup.parse(bookPage, "UTF-8"),
                        Jsoup.parse(bookPageNoSubtitleNoGenre, "UTF-8"));
        lastPageChecker = mock(LastPageChecker.class);
        when(lastPageChecker.extractLastPage(new Document(anyString()))).thenReturn(lastPageNo);
    }

    @Test
    public void shouldScrapBooksFromGivenHTML(){
        //given
        int booksOnPage = 2;
        Scraper scraper = new Scraper(webConnector, lastPageChecker);
        //when
        scraper.start(booksData -> assertEquals(booksOnPage, booksData.size()));
    }
}
