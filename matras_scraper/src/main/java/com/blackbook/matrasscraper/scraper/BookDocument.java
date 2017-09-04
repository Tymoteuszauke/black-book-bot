package com.blackbook.matrasscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
public class BookDocument {  
  
    private Document bookDoc;
  
    public BookDocument(Document bookDoc) {
        this.bookDoc = bookDoc;
    }

    public String extractBookTitle() {
        return bookDoc.select("div.product-title-inner-col.inner-col > h1[itemprop=name]")
                .text();
    }

    public Set<String> extractBookAuthors() {
        return bookDoc.getElementsByClass("title-author")
                .select("span[itemprop=name]")
                .stream()
                .map(Element::text)
                .collect(Collectors.toSet());
    }

    public String extractBookGenre() {
        return bookDoc.getElementsByClass("m-list")
                .first()
                .select("a > span")
                .text();
    }

    public String extractBookPrice() {
        return bookDoc.select("div.this-main-price")
                .attr("content");
    }

    public String extractBookPromoDetails() {
        return bookDoc.select("div.savings > span.bold")
                .text();
    }
}
