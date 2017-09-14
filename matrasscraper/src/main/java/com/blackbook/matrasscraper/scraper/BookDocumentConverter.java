package com.blackbook.matrasscraper.scraper;

import com.blackbook.utils.view.creationmodel.BookData;
import com.blackbook.utils.view.creationmodel.BookDiscountData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BookDocumentConverter {

    private BookDocumentConverter() {}

    static BookDiscountData createBookDiscountData(BookDocument bookDoc, String bookUrl) {
        String title = bookDoc.extractBookTitle();
        String subtitle = bookDoc.extractBookSubtitle();
        String authors = bookDoc.extractBookAuthors();
        String genre = bookDoc.extractBookGenre();
        Double price = bookDoc.extractBookPrice();
        String publisher = bookDoc.extractPublisher();
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
