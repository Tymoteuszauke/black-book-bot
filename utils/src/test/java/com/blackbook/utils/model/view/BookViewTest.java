package com.blackbook.utils.model.view;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class BookViewTest {

    private BookView bookView;
    private BookView otherBookView;

    @BeforeMethod
    public void before() {

        bookView = new BookView();
        bookView.setId(1L);
        bookView.setTitle("Pan Tymek");
        bookView.setSubtitle("Life");
        bookView.setAuthors("Tymke Wergiliusz");
        bookView.setPublisher("ACME");
        bookView.setBookPageUrl("www.tymek.pl");
        bookView.setCoverUrl("www.covers.com/tymek");

        otherBookView = new BookView();
        otherBookView.setId(1L);
        otherBookView.setTitle("Pan Tymek");
        otherBookView.setSubtitle("Life");
        otherBookView.setAuthors("Tymke Wergiliusz");
        otherBookView.setPublisher("ACME");
        otherBookView.setBookPageUrl("www.tymek.pl");
        otherBookView.setCoverUrl("www.covers.com/tymek");
    }

    @Test
    public void shouldReturnTrueForEqualObjects() throws Exception {
        // Then
        assertTrue(bookView.equals(otherBookView));
    }

    @Test
    public void sameObjectsShouldHaveSameHashCode() {
        // Then
        assertEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnSameStringsForSameObjects() throws Exception {
        // Then
        assertEquals(bookView.toString(), otherBookView.toString());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentId() throws Exception {
        // When
        otherBookView.setId(5L);
        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentId() {
        // When
        otherBookView.setId(5L);

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentTitle() throws Exception {
        // When
        otherBookView.setTitle("Pan Tymke");
        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentTitle() {
        // When
        otherBookView.setTitle("Pan Tymke");

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnFalseForWithDifferentSubtitles() throws Exception {
        // When
        otherBookView.setSubtitle("Biography");

        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentSubtitle() {
        // When
        otherBookView.setSubtitle("Biography");

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnFalseForWithDifferentAuthors() throws Exception {
        // When
        otherBookView.setAuthors("Patkkka");
        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentAuthors() {
        // When
        otherBookView.setAuthors("Patkkka");

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnFalseForWithDifferentPublishers() throws Exception {
        // When
        otherBookView.setPublisher("Books Publisher");
        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentPublishers() {
        // When
        otherBookView.setPublisher("Books Publisher");

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnFalseForWithDifferentBooksUrl() throws Exception {
        // When
        otherBookView.setBookPageUrl("www.fakeUrl.com");
        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentBookUrls() {
        // When
        otherBookView.setBookPageUrl("www.fakeUrl.com");

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }

    @Test
    public void shouldReturnFalseForWithDifferentCoversUrl() throws Exception {
        // When
        otherBookView.setBookPageUrl("www.fakeCoverUrl.com");
        // Then
        assertFalse(bookView.equals(otherBookView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentCoverUrls() {
        // When
        otherBookView.setBookPageUrl("www.fakeCoverUrl.com");

        // Then
        assertNotEquals(bookView.hashCode(), otherBookView.hashCode());
    }
}