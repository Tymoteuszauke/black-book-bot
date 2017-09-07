package com.blackbook.persistencebot.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookstoreTest {

    @DataProvider
    public Object[][] equalsAndHashCodeTestDataProvider() {
        return new Object[][]{
                {"Shop", "Details"},
                {null, "Details"},
                {"Shop", null},
        };
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testEquals(String name, String details) {
        // Given
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setName(name);
        bookstore1.setDetails(details);

        Bookstore bookstore2 = new Bookstore();
        bookstore2.setName(name);
        bookstore2.setDetails(details);

        // Then
        assertTrue(bookstore1.equals(bookstore2));
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testHashcode(String name, String details) {
        // Given
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setName(name);
        bookstore1.setDetails(details);

        Bookstore bookstore2 = new Bookstore();
        bookstore2.setName(name);
        bookstore2.setDetails(details);

        // Then
        assertEquals(bookstore1.hashCode(), bookstore2.hashCode());
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testToString(String name, String details) {
        // Given
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setName(name);
        bookstore1.setDetails(details);

        Bookstore bookstore2 = new Bookstore();
        bookstore2.setName(name);
        bookstore2.setDetails(details);

        // Then
        assertEquals(bookstore1.toString(), bookstore2.toString());
    }
}