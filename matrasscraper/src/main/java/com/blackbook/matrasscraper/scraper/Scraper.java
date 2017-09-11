package com.blackbook.matrasscraper.scraper;

import com.blackbook.matrasscraper.htmlprovider.HTMLDocumentProvider;
import core.CrawlerActionListener;
import core.ICrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
@Component
public class Scraper implements ICrawler {

    public static final int BOOKSTORE_ID = 1;

    @Value("${const.pages-limit}")
    int lastPageNo;

    private static final String MATRAS_URL = "http://www.matras.pl/ksiazki/promocje,k,53";
    private static final String MATRAS_URL_PAGE = MATRAS_URL + "?p=";
    private HTMLDocumentProvider htmlDocumentProvider;

    @Autowired
    public Scraper(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
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
    public void start(CrawlerActionListener actionListener, ExecutorService executorService) {
        Document mainPageDoc = htmlDocumentProvider.provide(MATRAS_URL);
        lastPageNo = Math.min(lastPageNo, extractLastPageNo(mainPageDoc));
        List<BookDiscountData> bookDiscountData = new LinkedList<>();
        for (int i = 0; i < lastPageNo; i++) {
            Document pageDoc = htmlDocumentProvider.provide(MATRAS_URL_PAGE + i);
            bookDiscountData.addAll(extractBookElementsFromSinglePage(pageDoc));
        }
        actionListener.crawlerFinished(bookDiscountData);
    }

    @Override
    public int getId() {
        return BOOKSTORE_ID;
    }
}
