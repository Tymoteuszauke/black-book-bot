package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    public final static int BOOKSTORE_ID = 1;
    private HTMLDocumentProvider htmlDocumentProvider;

    public Scraper(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
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
        return Integer.parseInt(mainPageDoc.select(".pagination-list > li:not(.next)")
                .last()
                .text());
    }

    private List<BookDiscountData> extractBookElementsFromSinglePage(Document document) {
        Elements bookElements = document.getElementsByClass("s-item eqh");
        List<BookDiscountData> bookDiscountData = bookElements.stream()
                .map(bookElement -> {
                    String bookUrl = extractBookUrl(bookElement);
                    BookDocument bookDoc = new BookDocument(htmlDocumentProvider.provide(bookUrl));
                    return BookDocumentConverter.createBookDiscountData(bookDoc, bookUrl);
                })
                .collect(Collectors.toList());
        return bookDiscountData;
    }

    private String extractBookUrl(Element bookElement) {
        return bookElement.getElementsByClass("right-side")
                .select("a")
                .first()
                .attr("href");
    }
}
