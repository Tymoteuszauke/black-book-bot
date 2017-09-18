package com.blackbook.persistencebot.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class LogEventModelTest {

    private LogEventModel logEventModel;
    private LogEventModel otherLogEventModel;
    private Bookstore bookstore;
    private Bookstore otherBookstore;

    @BeforeMethod
    public void before() {
        bookstore = new Bookstore();
        bookstore.setId(10L);
        bookstore.setName("Store");
        bookstore.setDetails("Great store");

        otherBookstore = new Bookstore();
        otherBookstore.setId(10L);
        otherBookstore.setName("Store");
        otherBookstore.setDetails("Great store");

        logEventModel = new LogEventModel();
        logEventModel.setId(1L);
        logEventModel.setStartTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 13, 30)));
        logEventModel.setFinishTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 14, 45)));
        logEventModel.setResult(10);
        logEventModel.setBookStore(bookstore);

        otherLogEventModel = new LogEventModel();
        otherLogEventModel.setId(1L);
        otherLogEventModel.setStartTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 13, 30)));
        otherLogEventModel.setFinishTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 14, 45)));
        otherLogEventModel.setResult(10);
        otherLogEventModel.setBookStore(otherBookstore);
    }

    @Test
    public void equalsShouldReturnTrueForSameObjects() throws Exception {
        // Then
        assertTrue(logEventModel.equals(otherLogEventModel));
    }

    @Test
    public void sameObjectsShouldHaveSameHashCode() throws Exception {
        // Then
        assertEquals(logEventModel.hashCode(), otherLogEventModel.hashCode());
    }

    @Test
    public void sameObjectsShouldHaveSameStrings() throws Exception {
        // Then
        assertEquals(logEventModel.toString(), otherLogEventModel.toString());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentId() throws Exception {
        // When
        otherLogEventModel.setId(2L);

        // Then
        assertFalse(logEventModel.equals(otherLogEventModel));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentId() throws Exception {
        // When
        otherLogEventModel.setId(2L);

        // Then
        assertNotEquals(logEventModel.hashCode(), otherLogEventModel.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentStartTime() throws Exception {
        // When
        otherLogEventModel.setStartTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 22, 40)));

        // Then
        assertFalse(logEventModel.equals(otherLogEventModel));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentStartTime() throws Exception {
        // When
        otherLogEventModel.setStartTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 22, 40)));

        // Then
        assertNotEquals(logEventModel.hashCode(), otherLogEventModel.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentFinishTime() throws Exception {
        // When
        otherLogEventModel.setFinishTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 22, 40)));

        // Then
        assertFalse(logEventModel.equals(otherLogEventModel));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentFinishTime() throws Exception {
        // When
        otherLogEventModel.setFinishTime(Timestamp.valueOf(LocalDateTime.of(2017, 9, 10, 22, 40)));

        // Then
        assertNotEquals(logEventModel.hashCode(), otherLogEventModel.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentResult() throws Exception {
        // When
        otherLogEventModel.setResult(55);

        // Then
        assertFalse(logEventModel.equals(otherLogEventModel));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentResult() throws Exception {
        // When
        otherLogEventModel.setResult(55);

        // Then
        assertNotEquals(logEventModel.hashCode(), otherLogEventModel.hashCode());
    }

    @Test
    public void shouldReturnFalseForObjectsWithDifferentBookstore() throws Exception {
        // When
        otherBookstore.setName("Other");
        otherLogEventModel.setBookStore(otherBookstore);

        // Then
        assertFalse(logEventModel.equals(otherLogEventModel));
    }

    @Test
    public void shouldReturnDifferentHashCodeForObjectsWithDifferentBookstore() throws Exception {
        // When
        otherBookstore.setName("Other");
        otherLogEventModel.setBookStore(otherBookstore);

        // Then
        assertNotEquals(logEventModel.hashCode(), otherLogEventModel.hashCode());
    }
}