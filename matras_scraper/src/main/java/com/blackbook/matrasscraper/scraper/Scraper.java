package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import com.blackbook.matrasscraper.htmlprovider.JsoupHTMLDocumentProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import view.creation_model.BookData;
import view.creation_model.BookDiscountData;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
public class Scraper {

    private final static String MATRAS_URL = "http://www.matras.pl/ksiazki/promocje,k,53";
    private final static String MATRAS_URL_PAGE = MATRAS_URL + "?p=";
    private final static int BOOKSTORE_ID = 1;
    private HTMLDocumentProvider htmlDocumentProvider;

    public Scraper(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
    }

    public static void main(String[] args) {
        Scraper scraper = new Scraper(new JsoupHTMLDocumentProvider());
        scraper.extractBookElements();
    }

    public List<BookDiscountData> extractBookElements() {
        Document mainPageDoc = htmlDocumentProvider.provide(MATRAS_URL);
        int lastPageNo = extractLastPageNo(mainPageDoc);
        List<BookDiscountData> bookDiscountData = new LinkedList<>();
        //TODO change 1 to property or lastPageNo if all pages wanted
        for (int i = 0; i < 1; i++) {
            Document pageDoc = htmlDocumentProvider.provide(MATRAS_URL_PAGE + i);
            bookDiscountData.addAll(extractBookElementsFromSinglePage(pageDoc));
        }
        return bookDiscountData;
    }

    private int extractLastPageNo(Document mainPageDoc) {
        return Integer.parseInt(mainPageDoc.select("ul.pagination-list > li:not(.next)")
                    .last()
                    .text());
    }

    private List<BookDiscountData> extractBookElementsFromSinglePage(Document document) {
        Elements bookElements = document.getElementsByClass("s-item eqh");
        List<BookDiscountData> bookDiscountData = bookElements.stream()
                .map(bookElement -> {
                    String bookUrl = extractBookUrl(bookElement);
                    BookDocument bookDoc = new BookDocument(htmlDocumentProvider.provide(bookUrl));
                    return createBookDiscountData(bookDoc, bookUrl);
                })
                .collect(Collectors.toList());
        return bookDiscountData;
    }

    private BookDiscountData createBookDiscountData(BookDocument bookDoc, String bookUrl) {
        String title = bookDoc.extractBookTitle();
        String subtitle = bookDoc.extractBookSubtitle();
        String authors = bookDoc.extractBookAuthors();
        String genre = bookDoc.extractBookGenre();
        Double price = bookDoc.extractBookPrice();
        String promoDetails = bookDoc.extractBookPromoDetails();
        String coverUrl = bookDoc.extractBookCoverUrl();

        BookDiscountData bookDiscountData = BookDiscountData.builder()
                .bookstoreId(BOOKSTORE_ID)
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
        System.out.println(title + separator + subtitle + separator + authors + separator + genre + separator +
                price + separator + promoDetails + separator + bookUrl + separator + coverUrl);
        return bookDiscountData;
    }

    private String extractBookUrl(Element bookElement) {
        return bookElement.getElementsByClass("right-side")
                .select("a")
                .first()
                .attr("href");
    }

}
