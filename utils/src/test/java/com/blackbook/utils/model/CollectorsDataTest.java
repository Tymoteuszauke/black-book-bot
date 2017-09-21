package com.blackbook.utils.model;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CollectorsDataTest {

    @Test
    public void shouldGetBookStoreId() throws Exception {
        // Then
        assertEquals(5, CollectorsData.GANDALF_SCRAPER.getBookStoreId());
        assertEquals(4, CollectorsData.GOOGLE_CRAWLER.getBookStoreId());
        assertEquals(3, CollectorsData.TANIA_KSIAZKA_SCRAPER.getBookStoreId());
        assertEquals(2, CollectorsData.CZYTAMPL_SCRAPER.getBookStoreId());
        assertEquals(1, CollectorsData.MATRAS_SCRAPER.getBookStoreId());
    }

    @Test
    public void shouldGetBaseUrl() throws Exception {
        // Then
        assertEquals("http://www.gandalf.com.pl", CollectorsData.GANDALF_SCRAPER.getBaseUrl());
        assertEquals("https://www.googleapis.com/books/v1/volumes?q=", CollectorsData.GOOGLE_CRAWLER.getBaseUrl());
        assertEquals("http://www.taniaksiazka.pl/tanie-ksiazki/page-%d", CollectorsData.TANIA_KSIAZKA_SCRAPER.getBaseUrl());
        assertEquals("http://czytam.pl", CollectorsData.CZYTAMPL_SCRAPER.getBaseUrl());
        assertEquals("http://www.matras.pl/ksiazki/promocje,k,53", CollectorsData.MATRAS_SCRAPER.getBaseUrl());
    }
}