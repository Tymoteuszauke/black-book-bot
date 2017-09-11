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
        log.info(readTitle(book));
        return BookDiscountData.builder()
                .bookDiscountDetails(book.select(".product-discount").text())
                .price(Double.valueOf(book.select("a").attr("data-price")))
                .bookstoreId(BOOKSTORE_ID)
                .bookData(BookData.builder()
                        .title(readTitle(book))
                        .subtitle(readSubtitle(book))
                        .genre(book.select("a").attr("data-category"))
                        .authors(book.select(".product-authors").text())
                        .bookPageUrl(BOOKSTORE_URL + book.select("a").next().attr("href"))
                        .coverUrl(book.select("img").attr("src"))
                        .build())
                .build();
    }

    private String readTitle(Element book) {
        return book
                .select("a")
                .attr("data-name")
                .split("\\.", 2)
                [0];
    }

    private String readSubtitle(Element book) {
        String[] titltes = book
                .select("a")
                .attr("data-name")
                .split("\\.", 2);
        return getSubtitle(titltes);
    }

    private String getSubtitle(String[] titltes) {
        return titltes.length == 2 ? titltes[1].trim() : null;
    }
}
