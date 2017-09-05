package com.blackbook.matrasscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
class BookDocument {
    private Document bookDoc;

    BookDocument(Document bookDoc) {
        this.bookDoc = bookDoc;
    }

    String extractBookTitle() {
        String title = extractBookTitles();
        int dotIndex = title.indexOf('.');
        if (dotIndex != -1) {
            title = title.substring(0, dotIndex).trim();
        }
        return title;
    }

    private String extractBookTitles() {
        return bookDoc.select(".product-title-inner-col.inner-col > [itemprop=name]")
                .text();
    }

    String extractBookSubtitle() {
        String subtitle = null;
        String title = extractBookTitles();
        int dotIndex = title.indexOf('.');
        if (dotIndex != -1) {
            subtitle = title.substring(dotIndex + 1).trim();
        }
        return subtitle;
    }

    String extractBookAuthors() {
        List<String> authorsList = bookDoc.getElementsByClass("title-author")
                .select("[itemprop=name]")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());
        return String.join(", ", authorsList);
    }

    String extractBookGenre() {
        return bookDoc.getElementsByClass("m-list")
                .first()
                .select("a > span")
                .text();
    }

    public Double extractBookPrice() {
        return Double.parseDouble(bookDoc.select(".this-main-price")
                .attr("content"));
    }

    public String extractBookPromoDetails() {
        return bookDoc.select(".savings > .bold")
                .text();
    }

    public String extractBookCoverUrl() {
        return bookDoc.select(".main-cover-inner-col.inner-col > [itemprop=image]")
                .attr("href");
    }
}
