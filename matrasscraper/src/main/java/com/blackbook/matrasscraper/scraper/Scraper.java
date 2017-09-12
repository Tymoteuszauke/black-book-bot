package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
@Slf4j
@Component
public class Scraper {

    @Value("${const.pages-limit}")
    int lastPageNo;

    private final static String MATRAS_URL = "http://www.matras.pl/ksiazki/promocje,k,53";
    private final static String MATRAS_URL_PAGE = MATRAS_URL + "?p=";
    private HTMLDocumentProvider htmlDocumentProvider;

    @Autowired
    public Scraper(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
    }

    public List<BookDiscountData> extractBookElements() {
        Document mainPageDoc = htmlDocumentProvider.provide(MATRAS_URL);
        lastPageNo = Math.min(lastPageNo, extractLastPageNo(mainPageDoc));
        List<BookDiscountData> bookDiscountData = new LinkedList<>();
        for (int i = 0; i < lastPageNo; i++) {
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
