package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

import java.util.List;

public class BookBuilder {
    private final static int BOOKSTORE_ID = 2;
    private final static String STORE_PAGE = "http://czytam.pl";
    private final String STRONG_TAGNAME_QUERY = "strong";

    BookDiscountData buildBookDiscountDataObject(Connector connector, Element book) {

        String bookDetailsUrl = STORE_PAGE + book
                .select(".image-container")
                .select("a")
                .attr("href")
                .replaceAll("\t", "")
                .replaceAll("\n", "");
        Document detailsPage = connector.getDocumentFromWebPage(bookDetailsUrl);

        return BookDiscountData.builder()
                .bookstoreId(BOOKSTORE_ID)
                .price(readBookPrice(book))
                .bookDiscountDetails(readPromoDetails(book))
                .bookData(BookData.builder()
                        .title(readBookTitle(detailsPage))
                        .subtitle(readBookSubtitle(detailsPage))
                        .authors(readBookAuthors(book))
                        .genre(readBookGenre(detailsPage))
                        .bookPageUrl(bookDetailsUrl)
                        .coverUrl(readBookCoverUrl(book))
                        .build())
                .build();
    }

    private Double readBookPrice(Element book) {
        List<String> prices = book.select(".product-price").select(STRONG_TAGNAME_QUERY).eachText();
        return Double.valueOf(prices
                .get(1)
                .replaceAll(",", ".")
                .replaceAll("[a-zA-Z]", ""));
    }

    private String readPromoDetails(Element book) {
        Elements promoDetails = book.select(".icon_rabat");
        return promoDetails.text().replaceAll("\\s+", "");
    }

    String readBookTitle(Document detailsPage) {
        Element details = detailsPage.getElementById("panel4-2");
        return details == null ? "-" : getTitle(details);
    }

    private String getTitle(Element details) {
        return details.html().contains("Tytuł") ? details.child(1).select(STRONG_TAGNAME_QUERY).text() : "-";
    }

    String readBookSubtitle(Document detailsPage) {
        Element details = detailsPage.getElementById("panel4-2");
        return details == null ? "-" : getSubtitle(details);
    }

    private String getSubtitle(Element details) {
        return details.html().contains("Podtytuł") ? details.child(2).select(STRONG_TAGNAME_QUERY).text() : "-";
    }

    private String readBookAuthors(Element book) {
        return book.select(".product-author").text();
    }

    private String readBookGenre(Document detailsPage) {
        String bookGenre = detailsPage.select(".level-2").select(".active").html();
        return bookGenre.equals("") ? "Unknown" : bookGenre;
    }

    private String readBookCoverUrl(Element book) {
        Elements promoDetails = book.select(".image-container").select("a[href]");
        return promoDetails.select("img").attr("src");
    }
}
