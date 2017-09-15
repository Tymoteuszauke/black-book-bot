package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import com.blackbook.utils.core.Collector;
import com.blackbook.utils.model.CollectorsData;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
@Component
public class Scraper implements Collector {

    @Value("${const.pages-limit}")
    int lastPageNo;

    private final String matrasUrlPage;
    private final HTMLDocumentProvider htmlDocumentProvider;
    private final CollectorsData collectorData;

    @Autowired
    public Scraper(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
        this.collectorData = CollectorsData.MATRAS_SCRAPER;
        this.matrasUrlPage = collectorData.getBaseUrl() + "?p=";
    }

    private int extractLastPageNo(Document mainPageDoc) {
        return Integer.parseInt(mainPageDoc.select(".pagination-list > li:not(.next)")
                .last()
                .text());
    }

    private List<BookDiscountData> extractBookElementsFromSinglePage(Document document) {
        Elements bookElements = document.getElementsByClass("s-item eqh");
        return bookElements.stream()
                .map(bookElement -> {
                    String bookUrl = extractBookUrl(bookElement);
                    BookDocument bookDoc = new BookDocument(htmlDocumentProvider.provide(bookUrl));
                    return BookDocumentConverter.createBookDiscountData(bookDoc, bookUrl);
                })
                .collect(Collectors.toList());
    }

    private String extractBookUrl(Element bookElement) {
        return bookElement.getElementsByClass("right-side")
                .select("a")
                .first()
                .attr("href");
    }

    @Override
    public void start(Consumer<List<BookDiscountData>> consumer) {
        Document mainPageDoc = htmlDocumentProvider.provide(collectorData.getBaseUrl());
        lastPageNo = Math.min(lastPageNo, extractLastPageNo(mainPageDoc));
        List<BookDiscountData> bookDiscountData = new LinkedList<>();
        for (int i = 0; i < lastPageNo; i++) {
            Document pageDoc = htmlDocumentProvider.provide(matrasUrlPage + i);
            bookDiscountData.addAll(extractBookElementsFromSinglePage(pageDoc));
        }
        consumer.accept(bookDiscountData);
    }

    @Override
    public int getId() {
        return collectorData.getBookStoreId();
    }
}
