package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.creation_model.BookData;
import view.creation_model.BookDiscountData;

import java.util.List;

public class BookBuilder {
    private final static int BOOKSTORE_ID = 2;
    private WebReader reader;
    private Element book;
    private Document detailsPage;

    public BookBuilder(WebReader reader, Element book) {
        this.reader = reader;
        this.book = book;
    }

    BookDiscountData buildBookDiscountDataObject() {

        String bookDetailsUrl = "http://czytam.pl" + book.select(".image-container").select("a").attr("href");
        detailsPage = reader.getDocumentFromWebPage(bookDetailsUrl);

        BookDiscountData bookDiscountData = BookDiscountData.builder()
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

        return bookDiscountData;
    }

    private Double readBookPrice() {
        List<String> prices = book.select(".product-price").select("strong").eachText();
        return Double.valueOf(prices.get(1).replaceAll(",", "."));
    }

    private String readPromoDetails() {
        Elements promoDetails = book.select(".image-container").select("a[href]");
        return promoDetails.text().replaceAll("\\s+", "");
    }

    private String readBookTitle() {
        Element details = detailsPage.getElementById("panel4-2");
        return details.html().contains("Tytuł") ? details.child(1).select("strong").text() : "-";
    }

    private String readBookSubtitle() {
        Element details = detailsPage.getElementById("panel4-2");
        return details.html().contains("Podtytuł") ? details.child(2).select("strong").text() : "-";
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
