package com.blackbook.taniaksiazkascraper.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

@Slf4j
@Component
public class PromoDetailsReader {
    private static final int BOOKSTORE_ID = 3;
    private static final String BOOKSTORE_URL = "http://www.taniaksiazka.pl";

    BookDiscountData readDiscountDataProperties(Element book) {
        log.info(book.select("a").attr("data-name"));
        return BookDiscountData.builder()
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
    }
}
