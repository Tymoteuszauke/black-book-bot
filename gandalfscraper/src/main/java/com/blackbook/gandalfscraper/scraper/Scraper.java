package com.blackbook.gandalfscraper.scraper;

import com.blackbook.gandalfscraper.webconnector.WebConnector;
import core.CrawlerActionListener;
import core.ICrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final int BOOKSTORE_ID = 4;

    public static final String GANDALF_BOOKSTORE_URL = "http://www.gandalf.com.pl";
    private static final String GANDALF_DISCOUNT_URL = GANDALF_BOOKSTORE_URL + "/k/okazje-do-60/";
    private WebConnector webConnector;
    private LastPageChecker lastPageChecker;

    @Autowired
    public Scraper(WebConnector webConnector, LastPageChecker lastPageChecker) {
        this.webConnector = webConnector;
        this.lastPageChecker = lastPageChecker;
    }

    private List<BookDiscountData> extractBookElements(int lastPageNo) {
        List<BookDiscountData> bookDiscountData = new LinkedList<>();
        for (int i = 0; i < lastPageNo; i++) {
            Document pageDoc = webConnector.connect(GANDALF_DISCOUNT_URL + i);
            bookDiscountData.addAll(extractBookElementsFromSinglePage(pageDoc));
        }
        return bookDiscountData;
    }

    private List<BookDiscountData> extractBookElementsFromSinglePage(Document document) {
        Elements bookElements = document.getElementsByClass("prod");
        return bookElements.stream()
                .map(bookElement -> {
                    String bookUrl = GANDALF_BOOKSTORE_URL + extractBookUrl(bookElement);
                    BookPage bookDoc = new BookPage(webConnector.connect(bookUrl));
                    return BookDiscountDataCreator.createBookDiscountDataFrom(bookDoc, bookUrl);
                })
                .collect(Collectors.toList());
    }

    private String extractBookUrl(Element bookElement) {
        return bookElement.select(".pdata > p > a")
                .first()
                .attr("href");
    }

    @Override
    public void start(CrawlerActionListener actionListener, ExecutorService executorService) {
        Document mainPageDoc = webConnector.connect(GANDALF_DISCOUNT_URL);
        int lastPageNo = lastPageChecker.extractLastPage(mainPageDoc);
        actionListener.crawlerFinished(extractBookElements(lastPageNo));
    }

    @Override
    public int getId() {
        return BOOKSTORE_ID;
    }
}
