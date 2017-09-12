package com.blackbook.matrasscraper.scraper;

import lombok.extern.slf4j.Slf4j;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

@Slf4j
class BookDocumentConverter {

    static BookDiscountData createBookDiscountData(BookDocument bookDoc, String bookUrl) {
        String title = bookDoc.extractBookTitle();
        String subtitle = bookDoc.extractBookSubtitle();
        String authors = bookDoc.extractBookAuthors();
        String genre = bookDoc.extractBookGenre();
        Double price = bookDoc.extractBookPrice();
        String promoDetails = bookDoc.extractBookPromoDetails();
        String coverUrl = bookDoc.extractBookCoverUrl();

        BookDiscountData bookDiscountData = BookDiscountData.builder()
                .bookstoreId(Scraper.BOOKSTORE_ID)
                .price(price)
                .bookDiscountDetails(promoDetails)
                .bookData(BookData.builder()
                        .title(title)
                        .subtitle(subtitle)
                        .authors(authors)
                        .genre(genre)
                        .bookPageUrl(bookUrl)
                        .coverUrl(coverUrl)
                        .build())
                .build();

        String separator = " - ";
       log.info(title + separator + subtitle + separator + authors + separator + genre + separator +
                price + separator + promoDetails + separator + bookUrl + separator + coverUrl);
        return bookDiscountData;
    }
}
