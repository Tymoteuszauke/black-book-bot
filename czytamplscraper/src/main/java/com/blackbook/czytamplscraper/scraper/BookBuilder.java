package com.blackbook.czytamplscraper.scraper;

import com.blackbook.utils.model.CollectorsData;
import com.blackbook.utils.model.creationmodel.BookData;
import com.blackbook.utils.model.creationmodel.BookDiscountData;
import org.jsoup.nodes.Document;

import java.util.List;

public class BookBuilder {

    private final CollectorsData collectorData = CollectorsData.CZYTAMPL_SCRAPER;

    BookDiscountData buildBookDiscountDataObject(Document detailsPage) {
        return BookDiscountData.builder()
                .bookstoreId(collectorData.getBookStoreId())
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
        return collectorData.getBaseUrl() + detailsPage.getElementById("panel3-1").select("a").attr("href");
    }

    Double readBookPrice(Document detailsPage) {
        return Double.valueOf(detailsPage
                .select(".price")
                .select("strong")
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

    String readBookPublisher(Document detailsPage) {
        return detailsPage.select("span[itemprop=publisher]").text();
    }

    String readBookAuthors(Document detailsPage) {
        List<String> strings = detailsPage.select(".headline-azure").eachText();
        if (!strings.isEmpty()) {
            return strings.get(0);
        } else {
            return "Unknown";
        }
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
