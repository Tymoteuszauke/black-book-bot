package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import java.util.List;

class BookBuilder {
    private final static int BOOKSTORE_ID = 2;
    private final static String STORE_PAGE = "http://czytam.pl";
    private final String STRONG_TAGNAME_QUERY = "strong";
    private Connector reader;
    private Element book;
    private Document detailsPage;

    BookBuilder(Connector connector, Element book) {
        this.book = book;
        this.reader = connector;
    }

    BookDiscountData buildBookDiscountDataObject() {

        String bookDetailsUrl = STORE_PAGE + book
                .select(".image-container")
                .select("a")
                .attr("href")
                .replaceAll("\t", "")
                .replaceAll("\n", "");
        detailsPage = reader.getDocumentFromWebPage(bookDetailsUrl);

        return BookDiscountData.builder()
                .bookstoreId(BOOKSTORE_ID)
                .price(readBookPrice())
                .bookDiscountDetails(readPromoDetails())
                .bookData(BookData.builder()
                        .title(readBookTitle())
                        .subtitle(readBookSubtitle())
                        .authors(readBookAuthors())
                        .genre(readBookGenre())
                        .bookPageUrl(bookDetailsUrl)
                        .coverUrl(readBookCoverUrl())
                        .build())
                .build();
    }

    private Double readBookPrice() {
        List<String> prices = book.select(".product-price").select(STRONG_TAGNAME_QUERY).eachText();
        return Double.valueOf(prices
                .get(1)
                .replaceAll(",", ".")
                .replaceAll("[a-zA-Z]", ""));
    }

    private String readPromoDetails() {
        Elements promoDetails = book.select(".icon_rabat");
        return promoDetails.text().replaceAll("\\s+", "");
    }

    private String readBookTitle() {
        Element details = detailsPage.getElementById("panel4-2");
        return details == null ? "-" : getTitle(details);
    }

    private String getTitle(Element details) {
        return details.html().contains("Tytuł") ? details.child(1).select(STRONG_TAGNAME_QUERY).text() : "-";
    }

    private String readBookSubtitle() {
        Element details = detailsPage.getElementById("panel4-2");
        return details == null ? "-" : getSubtitle(details);
    }

    private String getSubtitle(Element details) {
        return details.html().contains("Podtytuł") ? details.child(2).select(STRONG_TAGNAME_QUERY).text() : "-";
    }

    private String readBookAuthors() {
        return book.select(".product-author").text();
    }

    private String readBookGenre() {
        String bookGenre = detailsPage.select(".level-2").select(".active").html();
        return bookGenre.equals("") ? "Unknown" : bookGenre;
    }

    private String readBookCoverUrl() {
        Elements promoDetails = book.select(".image-container").select("a[href]");
        return promoDetails.select("img").attr("src");
    }
}
