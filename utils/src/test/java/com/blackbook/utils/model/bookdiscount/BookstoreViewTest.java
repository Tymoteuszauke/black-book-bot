package com.blackbook.utils.model.bookdiscount;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class BookstoreViewTest {

    private BookstoreView bookstoreView;
    private BookstoreView otherBookstoreView;

    @BeforeMethod
    public void before() {
        bookstoreView = new BookstoreView();
        bookstoreView.setId(1L);
        bookstoreView.setDetails("Great store");
        bookstoreView.setName("Store");

        otherBookstoreView = new BookstoreView();
        otherBookstoreView.setId(1L);
        otherBookstoreView.setDetails("Great store");
        otherBookstoreView.setName("Store");

    }

    @Test
    public void shouldReturnTrueForSameObjects() throws Exception {
        // Then
        assertTrue(bookstoreView.equals(otherBookstoreView));
    }

    @Test
    public void sameObjectsShouldHaveSameHashCode() {
        // Then
        assertEquals(bookstoreView.hashCode(), otherBookstoreView.hashCode());
    }

    @Test
    public void shouldReturnFalseForBookstoresWithDifferentId() {
        // When
        otherBookstoreView.setId(5L);

        // Then
        assertFalse(bookstoreView.equals(otherBookstoreView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentId() {
        // When
        otherBookstoreView.setId(5L);

        // Then
        assertNotEquals(bookstoreView.hashCode(), otherBookstoreView.hashCode());
    }

    @Test
    public void shouldReturnFalseForBookstoresWithDifferentName() {
        // When
        otherBookstoreView.setName("Other");

        // Then
        assertFalse(bookstoreView.equals(otherBookstoreView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentName() {
        // When
        otherBookstoreView.setName("Other");

        // Then
        assertNotEquals(bookstoreView.hashCode(), otherBookstoreView.hashCode());
    }

    @Test
    public void shouldReturnFalseForBookstoresWithDifferentDetails() {
        // When
        otherBookstoreView.setDetails("Fine Store");

        // Then
        assertFalse(bookstoreView.equals(otherBookstoreView));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentDetails() {
        // When
        otherBookstoreView.setDetails("Fine Store");

        // Then
        assertNotEquals(bookstoreView.hashCode(), otherBookstoreView.hashCode());
    }
}