package com.blackbook.czytamplscraper.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;

@Slf4j
class PromoPageReader {

    List<BookDiscountData> readAllDiscountsFromPage(Connector connector, String promotionUrl) {
        List<BookDiscountData> discountData = new LinkedList<>();
        Document promotionDoc = connector.getDocumentFromWebPage(promotionUrl);
        Elements elements = promotionDoc.select(".product");
        elements.forEach(element -> {
            log.info("Building book: " + element.text());
            BookBuilder bookBuilder = new BookBuilder(connector, element);
            discountData.add(bookBuilder.buildBookDiscountDataObject());
        });
        return discountData;
    }
}
