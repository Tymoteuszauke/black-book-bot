package com.blackbook.gandalfscraper.scraper;

import com.blackbook.utils.model.CollectorsData;
import com.blackbook.utils.model.creationmodel.BookData;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BookDiscountDataCreator {

    private BookDiscountDataCreator() {}

    static BookDiscountData createBookDiscountDataFrom(BookPage bookDoc, String bookUrl) {
        String title = bookDoc.extractBookTitle();
        String subtitle = bookDoc.extractBookSubtitle();
        String authors = bookDoc.extractBookAuthors();
        String genre = bookDoc.extractBookGenre();
        Double price = bookDoc.extractBookPrice();
        String promoDetails = bookDoc.extractBookPromoDetails();
        String coverUrl = bookDoc.extractBookCoverUrl();
        String publisher = bookDoc.extractPublisher();

        BookDiscountData bookDiscountData = BookDiscountData.builder()
                .bookstoreId(CollectorsData.GANDALF_SCRAPER.getBookStoreId())
                .price(price)
                .bookDiscountDetails(promoDetails)
                .bookData(BookData.builder()
                        .title(title)
                        .subtitle(subtitle)
                        .authors(authors)
                        .genre(genre)
                        .publisher(publisher)
                        .bookPageUrl(bookUrl)
                        .coverUrl(coverUrl)
                        .build())
                .build();

        String separator = " - ";
        log.info(title + separator + subtitle + separator + authors + separator + publisher + separator +
                genre + separator + price + separator + promoDetails + separator + bookUrl + separator + coverUrl);
        return bookDiscountData;
    }
}
