package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import view.creationmodel.BookDiscountData;

import java.util.LinkedList;
import java.util.List;

public class PromoPageReader {

    List<BookDiscountData> readAllDiscountsFromPage(Connector reader, String promotionUrl) {
        List<BookDiscountData> discountData = new LinkedList<>();
        Document promotionDoc = reader.getDocumentFromWebPage(promotionUrl);
        Elements elements = promotionDoc.select(".product");
        elements.forEach(element -> {
            BookBuilder bookBuilder = new BookBuilder(reader, element);
            discountData.add(bookBuilder.buildBookDiscountDataObject());
        });
        return discountData;
    }
}
