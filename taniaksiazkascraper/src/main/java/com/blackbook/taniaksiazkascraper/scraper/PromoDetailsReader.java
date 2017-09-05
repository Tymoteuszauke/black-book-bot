package com.blackbook.taniaksiazkascraper.scraper;

import org.jsoup.nodes.Element;
import view.creation_model.BookData;
import view.creation_model.BookDiscountData;

class PromoDetailsReader {
    private final static int BOOKSTORE_ID = 3;
    private static final String BOOKSTORE_URL = "http://www.taniaksiazka.pl";

    BookDiscountData readDiscountDataProperties(Element book) {
        BookDiscountData discountData = BookDiscountData.builder()
                .bookDiscountDetails(book.select(".product-discount").text())
                .price(Double.valueOf(book.select("a").attr("data-price")))
                .bookstoreId(BOOKSTORE_ID)
                .bookData(BookData.builder()
                        .title(book.select("a").attr("data-name"))
                        .subtitle("-")
                        .genre(book.select("a").attr("data-category"))
                        .authors(book.select(".product-authors").text())
                        .bookPageUrl(BOOKSTORE_URL + book.select("a").next().attr("href"))
                        .coverUrl(book.select("img").attr("src"))
                        .build())
                .build();
        return discountData;
    }
}
