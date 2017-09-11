package com.blackbook.gandalfscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author "Patrycja Zaremba"
 */
class BookPage {
    private Document bookDoc;

    BookPage(Document bookDoc) {
        this.bookDoc = bookDoc;
    }

    String extractBookTitle() {
        return extractBookTitles()[0];
    }

    private String[] extractBookTitles() {
        String titlesHtml = bookDoc.select(".pdata1 > [itemprop=name]")
                .html()
                .replaceAll("\\(twarda\\)","")
                .replaceAll("\\(miękka\\)","")
                .trim();
        return titlesHtml.split("<br>");
    }

    String extractBookSubtitle() {
        String subtitle = null;
        String[] titles = extractBookTitles();
        if (titles.length > 1) {
            subtitle = titles[1];
        }
        return subtitle;
    }

    String extractBookAuthors() {
        List<String> authorsList = bookDoc.select(".persons > p > [itemprop=author]")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());
        return String.join(", ", authorsList);
    }

    String extractBookGenre() {
        int mainGenreIndex = 1;
        Elements elements = bookDoc.select(".product_categories > a");
        String genre = "nieznany";
        if (elements.size() > 1) {
            genre = elements.remove(mainGenreIndex)
                    .text();
        }
        return genre;
    }

    public Double extractBookPrice() {
        return Double.parseDouble(bookDoc.select(".new_price[itemprop=price]")
                .text()
                .replace(",", "."));
    }

    public String extractPublisher() {
        return bookDoc.select(".pdata1 > p > a")
                .text();
    }

    public String extractBookPromoDetails() {
        return bookDoc.select(".cheaper_info")
                .text()
                .replaceAll("[a-żA-Ż]","")
                .trim();
    }

    public String extractBookCoverUrl() {
        return Scraper.GANDALF_BOOKSTORE_URL + bookDoc.getElementsByClass("pimage bigimg")
                .attr("src");
    }
}
