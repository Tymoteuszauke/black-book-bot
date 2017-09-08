package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
//    @Test
//    public void shouldBuildProperBookDiscountDataObject() throws Exception {
//        // Given
//        Document parse = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");
//
//        // When
//        BookDiscountData bookDiscountData = bookBuilder.buildBookDiscountDataObject(parse);
//
//        // Then
//        assertEquals(20.96, bookDiscountData.getPrice());
//        assertEquals(bookDiscountData.getBookData().getTitle(), "Drakulcio ma kłopoty Straszliwa historia w obrazkach");
//        assertEquals(bookDiscountData.getBookData().getSubtitle(), "Straszliwa historia w obrazkach");
//        assertEquals(bookDiscountData.getBookData().getAuthors(), "Pinkwart Magdalena, Pinkwart Sergiusz");
//        assertEquals(bookDiscountData.getBookDiscountDetails(), "-59%");
//        assertEquals(bookDiscountData.getBookData().getGenre(), "Unknown");
//        assertEquals(bookDiscountData.getBookData().getBookPageUrl(), "http://czytam.pl/k,ks_599195,Drakulcio-ma-klopoty-Straszliwa-historia-w-obrazkach-Pinkwart-Magdalena-Pinkwart-Sergiusz.html");
//        assertEquals(bookDiscountData.getBookData().getCoverUrl(), "http://webimage.pl/pics/073/5/822173.jpg");
//    }