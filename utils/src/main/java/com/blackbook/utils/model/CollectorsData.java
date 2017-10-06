package com.blackbook.utils.model;

/**
 * @author Siarhei Shauchenka at 15.09.17
 */
public enum CollectorsData {
    CZYTAMPL_SCRAPER(2, "http://czytam.pl"),
    GANDALF_SCRAPER(5, "http://www.gandalf.com.pl"),
    GOOGLE_CRAWLER(4, "https://www.googleapis.com/books/v1/volumes?q="),
    MATRAS_SCRAPER(1, "http://www.matras.pl/ksiazki/promocje,k,53"),
    TANIA_KSIAZKA_SCRAPER(3, "https://www.taniaksiazka.pl/tanie-ksiazki/page-%d");

    private int bookStoreId;
    private String baseUrl;

    CollectorsData(int bookStoreId, String baseUrl) {
        this.bookStoreId = bookStoreId;
        this.baseUrl = baseUrl;
    }

    public int getBookStoreId() {
        return bookStoreId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
