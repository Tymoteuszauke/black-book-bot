package com.blackbook.utils.view;

/**
 * @author Siarhei Shauchenka at 15.09.17
 */
public enum CollectorsData {
    CZYTAMPL_SCRAPER(2),
    GANDALF_SCRAPER(5),
    GOOGLE_CRAWLER(4),
    MATRAS_SCRAPER(1),
    TANIA_KSIAZKA_SCRAPER(3);

    private int bookStoreId;

    CollectorsData(int bookStoreId) {
        this.bookStoreId = bookStoreId;
    }

    public int getBookStoreId() {
        return bookStoreId;
    }
}
