package com.blackbook.matrasscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
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
        String title = extractBookTitles();
        int dotIndex = title.indexOf(".");
        if (dotIndex != -1) {
            title = title.substring(0, dotIndex).trim();
        }
        return title;
    }

    private String extractBookTitles() {
        return bookDoc.select("div.product-title-inner-col.inner-col > h1[itemprop=name]")
                .text();
    }

    public String extractBookSubtitle() {
        String subtitle = null;
        String title = extractBookTitles();
        int dotIndex = title.indexOf(".");
        if (dotIndex != -1) {
            subtitle = title.substring(dotIndex + 1).trim();
        }
        return subtitle;
    }

    public String extractBookAuthors() {
        List<String> authorsList = bookDoc.getElementsByClass("title-author")
                .select("span[itemprop=name]")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());
        return String.join(", ", authorsList);
    }

    public String extractBookGenre() {
        return bookDoc.getElementsByClass("m-list")
                .first()
                .select("a > span")
                .text();
    }

    public Double extractBookPrice() {
        return Double.parseDouble(bookDoc.select("div.this-main-price")
                .attr("content"));
    }

    public String extractBookPromoDetails() {
        return bookDoc.select("div.savings > span.bold")
                .text();
    }

    public String extractBookCoverUrl() {
        return bookDoc.select("div.main-cover-inner-col.inner-col > a[itemprop=image]")
                .attr("href");
    }
}
