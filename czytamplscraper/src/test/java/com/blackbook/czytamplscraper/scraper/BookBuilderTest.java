package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@Test
public class BookBuilderTest {

    private final File BOOK_DETAILS_PAGE_HTML_FILE = new File("src/test/resources/book_details_page.html");
    private final File BOOK_DETAILS_NO_SUBTITLE_PAGE_HTML_FILE = new File("src/test/resources/book_details_page_no_subtitle.html");
    private final File BOOK_DETAILS_WITH_GENRE_PAGE_HTML_FILE = new File("src/test/resources/book_details_page_with_genre.html");
    private BookBuilder bookBuilder;

    public BookBuilderTest(BookBuilder bookBuilder) {
        this.bookBuilder = bookBuilder;
    }

    @Test
    public void shouldReadBookDetailsUrl() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String pageUrl = bookBuilder.getReadPageUrl(document);

        // Then
        assertEquals(pageUrl,
                "http://czytam.pl/k,ks_292286,Czartoryscy.-Opowiesc-fotograficzna-Caillot-Dubus-Barbara-Brzezinski-Marcin.html");
    }

    @Test
    public void shouldReadBookPrice() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        Double price = bookBuilder.readBookPrice(document);

        // Then
        assertEquals(price, 20.96);
    }

    @Test
    public void shouldReadPromoDetails() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String details = bookBuilder.readPromoDetails(document);

        // Then
        assertEquals(details, "-58%");
    }

    @Test
    public void shouldReadBookTitle() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String title = bookBuilder.readBookTitle(document);

        // Then
        assertEquals(title, "Czartoryscy");
    }

    @Test
    public void shouldReadBookSubtitle() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String title = bookBuilder.readBookSubtitle(document);

        // Then
        assertEquals(title, "Opowieść fotograficzna");
    }

    @Test
    public void shouldReadNullForBookWithNoSubtitle() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_NO_SUBTITLE_PAGE_HTML_FILE, "UTF-8");

        // When
        String title = bookBuilder.readBookSubtitle(document);

        // Then
        assertNull(title);
    }

    @Test
    public void shouldReadBookAuthors() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String title = bookBuilder.readBookAuthors(document);

        // Then
        assertEquals(title, "Caillot-Dubus Barbara, Brzeziński Marcin");
    }

    @Test
    public void shouldReadNullForNoBookGenre() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String genre = bookBuilder.readBookGenre(document);

        // Then
        assertNull(genre);
    }

    @Test
    public void shouldReadBookGenre() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_WITH_GENRE_PAGE_HTML_FILE, "UTF-8");

        // When
        String genre = bookBuilder.readBookGenre(document);

        // Then
        assertEquals(genre, "Zdrowie i uroda");
    }

    @Test
    public void shouldReadBookCoveUrl() throws IOException {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String coverUrl = bookBuilder.readBookCoverUrl(document);

        // Then
        assertEquals(coverUrl, "http://webimage.pl/pics/629/9/d632775.jpg");
    }
}