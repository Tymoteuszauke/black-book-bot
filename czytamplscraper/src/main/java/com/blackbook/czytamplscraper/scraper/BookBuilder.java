package com.blackbook.czytamplscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.creationmodel.BookData;
import view.creationmodel.BookDiscountData;

public class BookBuilder {
    private final static int BOOKSTORE_ID = 2;
    private final static String STORE_PAGE = "http://czytam.pl";
    private final String STRONG_TAGNAME_QUERY = "strong";

    BookDiscountData buildBookDiscountDataObject(Document detailsPage) {
        return BookDiscountData.builder()
                .bookstoreId(BOOKSTORE_ID)
                .price(readBookPrice(detailsPage))
                .bookDiscountDetails(readPromoDetails(detailsPage))
                .bookData(BookData.builder()
                        .title(readBookTitle(detailsPage))
                        .subtitle(readBookSubtitle(detailsPage))
                        .authors(readBookAuthors(detailsPage))
                        .genre(readBookGenre(detailsPage))
                        .bookPageUrl(getReadPageUrl(detailsPage))
                        .coverUrl(readBookCoverUrl(detailsPage))
                        .build())
                .build();
    }

    String getReadPageUrl(Document detailsPage) {
        return STORE_PAGE + detailsPage.getElementById("panel3-1").select("a").attr("href");
    }

    Double readBookPrice(Document detailsPage) {
        return Double.valueOf(detailsPage
                .select(".price")
                .select(STRONG_TAGNAME_QUERY)
                .eachText()
                .get(0)
                .replaceAll(",", ".")
                .replaceAll("[a-zA-Z]", "")
                .trim());
    }

    String readPromoDetails(Document detailsPage) {
        return "-" + detailsPage
                .select(".save")
                .eachText()
                .get(0)
                .replaceAll("[a-żA-Ż]", "")
                .trim();
    }

    String readBookTitle(Document detailsPage) {
        return detailsPage
                .select(".show-for-medium-up")
                .select("h1")
                .eachText()
                .get(0)
                .split("\\.", 2)
                [0];
    }

    String readBookSubtitle(Document detailsPage) {
        String[] titles = detailsPage
                .select(".show-for-medium-up")
                .select("h1")
                .eachText()
                .get(0)
                .split("\\.", 2);
        return getSubtitle(titles);
    }

    private String getSubtitle(String[] titles) {
        return titles.length == 2 ? titles[1].trim() : null;
    }

    String readBookAuthors(Document detailsPage) {
        return detailsPage.select(".headline-azure").eachText().get(0);
    }

    String readBookGenre(Document detailsPage) {
        String bookGenre = detailsPage.select(".level-2").select(".active").html();
        return bookGenre.equals("") ? null : bookGenre;
    }

    String readBookCoverUrl(Document detailsPage) {
        return detailsPage
                .getElementById("panel3-1")
                .select("img")
                .attr("src")
                .trim();
    }
}
