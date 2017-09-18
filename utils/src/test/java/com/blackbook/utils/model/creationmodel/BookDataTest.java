package com.blackbook.utils.model.creationmodel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class BookDataTest {

    private BookData bookData;
    private BookData otherBookData;

    @BeforeMethod
    public void before() {
        bookData = BookData.builder()
                .title("Pan Tymek")
                .subtitle("Life")
                .genre("Biografia")
                .authors("Tymke Wergiliusz")
                .publisher("ACME")
                .bookPageUrl("www.tymek.pl")
                .coverUrl("www.covers.com/tymek")
                .build();

        otherBookData = BookData.builder()
                .title("Pan Tymek")
                .subtitle("Life")
                .genre("Biografia")
                .authors("Tymke Wergiliusz")
                .publisher("ACME")
                .bookPageUrl("www.tymek.pl")
                .coverUrl("www.covers.com/tymek")
                .build();
    }

    @Test
    public void shouldBuildBookData() throws Exception {
        // Then
        assertEquals(bookData.getTitle(), "Pan Tymek");
        assertEquals(bookData.getSubtitle(), "Life");
        assertEquals(bookData.getGenre(), "Biografia");
        assertEquals(bookData.getAuthors(), "Tymke Wergiliusz");
        assertEquals(bookData.getPublisher(), "ACME");
        assertEquals(bookData.getBookPageUrl(), "www.tymek.pl");
        assertEquals(bookData.getCoverUrl(), "www.covers.com/tymek");
    }

    @Test
    public void shouldReturnTrueForEqualsObjects() {
        // Then
        assertTrue(bookData.equals(otherBookData));
    }

    @Test
    public void sameObjectsShouldHaveSameHashCode() {
        // Then
        assertEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnSameStringsForSameObjects() throws Exception {
        // Then
        assertEquals(bookData.toString(), otherBookData.toString());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentTitle() {
        // When
        otherBookData.setTitle("Pan Tymke");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentTitle() {
        // When
        otherBookData.setTitle("Pan Tymke");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentSubtitle() {
        // When
        otherBookData.setSubtitle("History");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentSubtitle() {
        // When
        otherBookData.setSubtitle("History");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentGenre() {
        // When
        otherBookData.setGenre("SF");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentGenre() {
        // When
        otherBookData.setGenre("SF");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentAuthors() {
        // When
        otherBookData.setAuthors("Patkkka");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentAuthors() {
        // When
        otherBookData.setAuthors("Patkkka");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentPublisher() {
        // When
        otherBookData.setPublisher("Books C.O");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentPublisher() {
        // When
        otherBookData.setPublisher("Books C.O");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentPageUrl() {
        // When
        otherBookData.setBookPageUrl("www.tymke.pl");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentPageUrl() {
        // When
        otherBookData.setBookPageUrl("www.tymke.pl");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentCoverUrl() {
        // When
        otherBookData.setCoverUrl("www.covers.com/tymke");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentCoverUrl() {
        // When
        otherBookData.setCoverUrl("www.covers.com/tymke");

        // Then
        assertNotEquals(bookData.hashCode(), otherBookData.hashCode());
    }
}