package com.blackbook.czytamplscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import view.creationmodel.BookDiscountData;
import java.io.File;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


public class BookBuilderTest {

    private final File BOOK_DETAILS_PAGE_HTML_FILE = new File("src/test/resources/book_details_page.html");
    private final File BOOK_DETAILS_NO_SUBTITLE_PAGE_HTML_FILE = new File("src/test/resources/book_details_page_no_subtitle.html");
    private final File BOOK_DETAILS_WITH_GENRE_PAGE_HTML_FILE = new File("src/test/resources/book_details_page_with_genre.html");
    private final File BOOK_DETAILS_NO_AUTHORS_PAGE_HTML_FILE = new File("src/test/resources/book_details_page_no_authors.html");
    private BookBuilder bookBuilder;

    @BeforeMethod
    public void before() {
        bookBuilder = new BookBuilder();
    }

    @Test
    public void testGetReadPageUrl() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String pageUrl = bookBuilder.getReadPageUrl(document);

        // Then
        assertEquals(pageUrl,
                "http://czytam.pl/k,ks_292286,Czartoryscy.-Opowiesc-fotograficzna-Caillot-Dubus-Barbara-Brzezinski-Marcin.html");
    }

    @Test
    public void testReadBookPrice() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        Double price = bookBuilder.readBookPrice(document);

        // Then
        assertEquals(price, 20.96);
    }

    @Test
    public void testReadPromoDetails() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String details = bookBuilder.readPromoDetails(document);

        // Then
        assertEquals(details, "-58%");
    }

    @Test
    public void testReadBookTitle() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String title = bookBuilder.readBookTitle(document);

        // Then
        assertEquals(title, "Czartoryscy");
    }

    @Test
    public void testReadBookSubtitle() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String subtitle = bookBuilder.readBookSubtitle(document);

        // Then
        assertEquals(subtitle, "Opowieść fotograficzna");
    }

    @Test
    public void shouldReadNullForBookWithNoSubtitle() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_NO_SUBTITLE_PAGE_HTML_FILE, "UTF-8");

        // When
        String subtitle = bookBuilder.readBookSubtitle(document);

        // Then
        assertNull(subtitle);
    }

    @Test
    public void testReadBookAuthors() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String authors = bookBuilder.readBookAuthors(document);

        // Then
        assertEquals(authors, "Caillot-Dubus Barbara, Brzeziński Marcin");
    }
    @Test
    public void shouldReturnUnknownForBookWithNoAuthors() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_NO_AUTHORS_PAGE_HTML_FILE, "UTF-8");

        // When
        String authors = bookBuilder.readBookAuthors(document);

        // Then
        assertEquals(authors, "Unknown");
    }


    @Test
    public void testReadBookGenre() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_WITH_GENRE_PAGE_HTML_FILE, "UTF-8");

        // When
        String genre = bookBuilder.readBookGenre(document);

        // Then
        assertEquals(genre, "Zdrowie i uroda");
    }

    @Test
    public void shouldReadNullForNoBookGenre() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String genre = bookBuilder.readBookGenre(document);

        // Then
        assertNull(genre);
    }

    @Test
    public void testReadBookCoverUrl() throws Exception {
        // Given
        Document document = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        String coverUrl = bookBuilder.readBookCoverUrl(document);

        // Then
        assertEquals(coverUrl, "http://webimage.pl/pics/629/9/d632775.jpg");
    }

    @Test
    public void shouldBuildBookDiscountDataObject() throws Exception {
        // Given
        Document doc = Jsoup.parse(BOOK_DETAILS_PAGE_HTML_FILE, "UTF-8");

        // When
        BookDiscountData discountData = bookBuilder.buildBookDiscountDataObject(doc);

        // Then
        assertEquals(discountData.getBookstoreId(), new Integer("2"));
        assertEquals(discountData.getPrice(), 20.96);
        assertEquals(discountData.getBookData().getAuthors(), "Caillot-Dubus Barbara, Brzeziński Marcin");
        assertEquals(discountData.getBookData().getTitle(), "Czartoryscy");
    }
}