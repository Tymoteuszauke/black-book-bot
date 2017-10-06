package com.blackbook.gandalfscraper.scraper;

import com.blackbook.gandalfscraper.webconnector.WebConnector;
import com.blackbook.utils.core.Collector;
import com.blackbook.utils.model.CollectorsData;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author "Patrycja Zaremba"
 */
@Component
public class Scraper implements Collector {

    private final String discountUrl;
    private final WebConnector webConnector;
    private final LastPageChecker lastPageChecker;
    private final CollectorsData collectorData;

    @Autowired
    public Scraper(WebConnector webConnector, LastPageChecker lastPageChecker) {
        this.webConnector = webConnector;
        this.lastPageChecker = lastPageChecker;
        this.collectorData = CollectorsData.GANDALF_SCRAPER;
        this.discountUrl = collectorData.getBaseUrl() + "/k/okazje-do-60/";
    }

    private List<BookDiscountData> extractBookElements(int lastPageNo) {
        List<BookDiscountData> bookDiscountData = new LinkedList<>();
        IntStream.rangeClosed(1, lastPageNo)
                .parallel()
                .forEach(i -> {
                    Document pageDoc = webConnector.connect(discountUrl + i);
                    bookDiscountData.addAll(extractBookElementsFromSinglePage(pageDoc));
                });
        return bookDiscountData;
    }

    private List<BookDiscountData> extractBookElementsFromSinglePage(Document document) {
        Elements bookElements = document.getElementsByClass("prod");
        return bookElements.stream()
                .parallel()
                .map(bookElement -> {
                    String bookUrl = extractBookUrl(bookElement);
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
    public void start(Consumer<List<BookDiscountData>> consumer) {
        Document mainPageDoc = webConnector.connect(discountUrl);
        int lastPageNo = lastPageChecker.extractLastPage(mainPageDoc);
        consumer.accept(extractBookElements(lastPageNo));
    }

    @Override
    public int getId() {
        return collectorData.getBookStoreId();
    }
}
