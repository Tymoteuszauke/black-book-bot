package com.blackbook.persistencebot.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookTest {

    @DataProvider
    public Object[][] equalsAndHashCodeTestDataProvider() {
        return new Object[][]{
                {"Title", "Subtitle", "Genre"},
                {null, "Subtitle", "Genre"},
                {"Title", null, "Genre"},
                {"Title", "Subtitle", null},
                {"Title", "Subtitle", null},
                {"Title", null, null},
                {null, "Subtitle", null},
                {null, null, "Genre"}
        };
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testEquals(String title, String subtitle, String genre) {
        // Given
        Book book1 = new Book();
        book1.setTitle(title);
        book1.setSubtitle(subtitle);
        book1.setGenre(genre);

        Book book2 = new Book();
        book2.setTitle(title);
        book2.setSubtitle(subtitle);
        book2.setGenre(genre);

        // Then
        assertTrue(book1.equals(book2));
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testHashCode(String title, String subtitle, String genre) {
        // Given
        Book book1 = new Book();
        book1.setTitle(title);
        book1.setSubtitle(subtitle);
        book1.setGenre(genre);

        Book book2 = new Book();
        book2.setTitle(title);
        book2.setSubtitle(subtitle);
        book2.setGenre(genre);

        // Then
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test(dataProvider = "equalsAndHashCodeTestDataProvider")
    public void testToString(String title, String subtitle, String genre) {
        // Given
        Book book1 = new Book();
        book1.setTitle(title);
        book1.setSubtitle(subtitle);
        book1.setGenre(genre);

        Book book2 = new Book();
        book2.setTitle(title);
        book2.setSubtitle(subtitle);
        book2.setGenre(genre);

        // Then
        assertEquals(book1.toString(), book2.toString());
    }
}