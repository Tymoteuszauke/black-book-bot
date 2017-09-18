package com.blackbook.utils.model.creationmodel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
    public void shouldReturnFalseForNotEqualsObjects() {
        // When
        otherBookData.setTitle("Pan Tymke");
        otherBookData.setSubtitle("History");
        otherBookData.setGenre("SF");
        otherBookData.setAuthors("Patkkka");
        otherBookData.setPublisher("Books C.O");
        otherBookData.setBookPageUrl("www.tymke.pl");
        otherBookData.setCoverUrl("www.covers.com/tymke");

        // Then
        assertFalse(bookData.equals(otherBookData));
    }
}