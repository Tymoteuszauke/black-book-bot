package com.blackbook.gandalfscraper.scraper;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author "Patrycja Zaremba"
 */
public class LastPageCheckerTest {

    @Test
    public void shouldExtractLastPage() throws IOException {
        //given
        int lastPageNo = 4;
        File pagingPage = new File("src/test/resources/paging.html");
        LastPageChecker lastPageChecker = new LastPageChecker();
        //when
        int extractedLastPageNo = lastPageChecker.extractLastPage(Jsoup.parse(pagingPage,
                "UTF-8"));
        //then
        assertEquals(lastPageNo, extractedLastPageNo);
    }
}
